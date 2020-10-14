package com.obl.book.services;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.obl.book.exceptions.OpenBookLibraryException;
import com.obl.book.models.Book;
import com.obl.book.models.BookDto;
import com.obl.book.models.BookAuthor;
import com.obl.book.models.BookAuthorApi;
import com.obl.book.models.BookCategory;
import com.obl.book.models.BookCategoryApi;
import com.obl.book.models.BookPublisher;
import com.obl.book.models.BookPublisherApi;
import com.obl.book.models.InventoryReport;
import com.obl.book.models.BookTransaction;
import com.obl.book.models.BookTransactionDto;
import com.obl.book.models.TopBorrowedBooks;
import com.obl.book.models.common.LibraryConstants;
import com.obl.book.models.common.Role;
import com.obl.book.models.common.User;
import com.obl.book.models.enums.BookTransactionStatus;
import com.obl.book.repos.BookAuthorRepo;
import com.obl.book.repos.BookCategoryRepo;
import com.obl.book.repos.BookPublisherRepo;
import com.obl.book.repos.BookRepo;
import com.obl.book.repos.BookTransactionRepo;
import com.obl.book.repos.common.LibraryConstantsRepo;
import com.obl.book.repos.common.UserRepo;
import com.obl.book.utils.BookUtility;


@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepo bookRepo;

	@Autowired
	BookTransactionRepo bookTransactionRepo;

	@Autowired
	BookAuthorRepo bookAuthorRepo;

	@Autowired
	BookPublisherRepo bookPublisherRepo;

	@Autowired
	BookCategoryRepo bookCategoryRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	BookTransactionRepo transactionRepo;
	
	@Autowired
	LibraryConstantsRepo constantsRepo;

	@Autowired
	private RestTemplate restTemplate;



	@Override
	public BookTransaction borrowBook(Integer bookId, Integer borrowedToUserId, java.util.Date borrowDate) throws OpenBookLibraryException {

		//check if book is already booked, availableCopies are zero and throw exception if booked

		Optional<User> optionalBorrowedToUser = userRepo.findById(borrowedToUserId);
		if(optionalBorrowedToUser.isEmpty()) {
			throw new OpenBookLibraryException("Account with UserId " + borrowedToUserId + " doesn't exist for User", HttpStatus.NOT_FOUND);
		}
		Optional<BookTransaction> _bookTransaction = bookTransactionRepo.checkIfBookIsBorrowed(borrowedToUserId, bookId);
		if(_bookTransaction.isPresent()) {
			throw new OpenBookLibraryException("This book is already in borrow state", HttpStatus.FORBIDDEN);
		}
		
		Book borrowedBook = BookUtility.utilityObject.getBookFromBookId(bookId, bookRepo);
		
		List<LibraryConstants> constantsList = constantsRepo.findAll();
		if(constantsList.size() == 0) {
			throw new OpenBookLibraryException("Constants are not initialized", HttpStatus.FAILED_DEPENDENCY);
		} else if(constantsList.size() > 1) {
			throw new OpenBookLibraryException("More than one row in constants table, which to choose ?", HttpStatus.FAILED_DEPENDENCY);
		}
		Integer borrowCount = bookTransactionRepo.countCurrentlyBorrowedBooks(optionalBorrowedToUser.get().getUserId());
		Integer maxLimit = constantsList.get(constantsList.size() - 1).getMaximumBooksLimit();
		if(maxLimit <= borrowCount) {
			throw new OpenBookLibraryException("Maximum Borrow Limit Reached, more than " + maxLimit +" books borrowed" , HttpStatus.FORBIDDEN);
		}
		
	
		BookTransaction bookTransaction = new BookTransaction();
		
		
		
		Date bookBorrowDate = new Date(borrowDate.getTime());
		bookTransaction.setBorrowedDate(bookBorrowDate);
		bookTransaction.setBorrowedToUser(optionalBorrowedToUser.get());
		bookTransaction.setBorrowedBook(borrowedBook);

		
		
		
		
		Date returnDate = Date.valueOf(bookBorrowDate.toLocalDate().plusDays(constantsList.get(constantsList.size() - 1).getBookBorrowDays()));
		bookTransaction.setReturnDate(returnDate);
		bookTransaction.setBookStatus(BookTransactionStatus.BORROWED);
		Integer currentCopies = borrowedBook.getBookAvailableCopies();

		if(currentCopies == 0) {

			borrowedBook.setIsAvailable(false);
			throw new OpenBookLibraryException("Book is not available", HttpStatus.NOT_FOUND);

		}
		borrowedBook.setBookAvailableCopies(currentCopies - 1);
		if (borrowedBook.getBookAvailableCopies() == 0) {
			borrowedBook.setIsAvailable(false);
		}
		bookTransaction = bookTransactionRepo.save(bookTransaction);
		
		restTemplate.postForObject("http://library-mail-service/notify/book-borrowed", bookTransaction, Object.class);
		return bookTransaction;
	}

	@Override
	public BookCategory addBookCategory(BookCategory category) throws OpenBookLibraryException {

		category = bookCategoryRepo.save(category);
		return category;
	}

	@Override
	public BookAuthor addAuthor(BookAuthor author) throws OpenBookLibraryException {
		author = bookAuthorRepo.save(author);
		return author;
	}

	@Override
	public BookPublisher addPublisher(BookPublisher publisher) throws OpenBookLibraryException {
		publisher = bookPublisherRepo.save(publisher);
		return publisher;
	}

	@Override
	public Boolean addBooks(List<Book> books) throws OpenBookLibraryException {
		for (Book book : books) {
			BookAuthor author = bookAuthorRepo.checkIfAuthorExistsByName(book.getBookAuthor().getAuthorName().toLowerCase());
			if(author == null) {

				author = bookAuthorRepo.save(book.getBookAuthor());
			}

			BookPublisher publisher = bookPublisherRepo.checkIfPublisherExistsByName(book.getBookPublisher().getPublisherName().toLowerCase());
			if(publisher == null) {
				publisher = bookPublisherRepo.save(book.getBookPublisher());
			}

			book.setBookAuthor(author);
			book.setBookPublisher(publisher);
			bookRepo.save(book);
		}
		return true;
	}

	@Override
	public Book updateBook(Book book) throws OpenBookLibraryException {
		
		BookAuthor author = bookAuthorRepo.checkIfAuthorExistsByName(book.getBookAuthor().getAuthorName().toLowerCase());
		if(author == null) {

			author = bookAuthorRepo.save(book.getBookAuthor());
		}

		BookPublisher publisher = bookPublisherRepo.checkIfPublisherExistsByName(book.getBookPublisher().getPublisherName().toLowerCase());
		if(publisher == null) {
			publisher = bookPublisherRepo.save(book.getBookPublisher());
		}

		book.setBookAuthor(author);
		book.setBookPublisher(publisher);
		
		Book toUpdateBook = BookUtility.utilityObject.getBookFromBookId(book.getBookId(), bookRepo);
		
		Integer diff = toUpdateBook.getBookCopies() - toUpdateBook.getBookAvailableCopies();
		
		Integer shouldAvailableCopies = book.getBookCopies() - diff;
		
		book.setBookAvailableCopies(shouldAvailableCopies);
		
		if(shouldAvailableCopies == 0) {
			book.setIsAvailable(false);
		}
		
		if(book.getIsAvailable() && !toUpdateBook.getIsAvailable()) {
			
			if(toUpdateBook.getToNotifyUsers() != null && toUpdateBook.getToNotifyUsers().size() > 0) {
				restTemplate.postForObject("http://library-mail-service/notify/book-available", toUpdateBook, Object.class);
				
				book.setToNotifyUsers(new ArrayList<User>());
			}
			
		}
		
		book = bookRepo.save(book);
		return book;

	}



	@Override
	public BookTransactionDto viewBorrowedBooks(Integer userId,Optional<Integer> pageNo,Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
		Pageable paging;
		Sort sort = null;
		BookTransactionDto bookTransactionWrapper = new BookTransactionDto();
		if(sortByColumn.isPresent() && sortDirection.isPresent()) {
			if(sortDirection.get().equals("desc")) {
				sort = Sort.by(sortByColumn.get()).descending();
			} else {
				sort = Sort.by(sortByColumn.get()).ascending();
			}
		}

		if(pageNo.isPresent() && pageSize.isPresent()) {
			if(sort != null) {
				paging = PageRequest.of(pageNo.get(), pageSize.get(),sort);
			} else {
				paging = PageRequest.of(pageNo.get(), pageSize.get());
			}
			Page<BookTransaction> page = bookTransactionRepo.viewBooksBorrowedById(userId,paging);
			bookTransactionWrapper.setBooks(page.toList());
			bookTransactionWrapper.setTotal_count(page.getTotalElements());
			return bookTransactionWrapper;
		}

		if(sort != null) {
			List<BookTransaction> books = bookTransactionRepo.findAll(sort);
			bookTransactionWrapper.setBooks(books);
			bookTransactionWrapper.setTotal_count(Long.valueOf(books.size()));
			return bookTransactionWrapper;
		}
		List<BookTransaction> books = bookTransactionRepo.findAll();
		bookTransactionWrapper.setBooks(books);
		bookTransactionWrapper.setTotal_count(Long.valueOf(books.size()));
		return bookTransactionWrapper;

	}

	@Override
	public BookDto viewAllBooks(Integer selectedCategoryId, Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {

		Pageable paging;
		Sort sort = null;
		BookDto bookWrapper = new BookDto();
		if(sortByColumn.isPresent() && sortDirection.isPresent()) {
			if(sortDirection.get().equals("desc")) {
				sort = Sort.by(sortByColumn.get()).descending();
			} else {
				sort = Sort.by(sortByColumn.get()).ascending();
			}
		}

		if(pageNo.isPresent() && pageSize.isPresent()) {
			if(sort != null) {
				paging = PageRequest.of(pageNo.get(), pageSize.get(),sort);
			} else {
				paging = PageRequest.of(pageNo.get(), pageSize.get());
			}
			Page<Book> page = bookRepo.getBooksOfCategory(selectedCategoryId, paging);
			bookWrapper.setBooks(page.toList());
			bookWrapper.setTotal_count(page.getTotalElements());
			return bookWrapper;
		}

		if(sort != null) {
			List<Book> books = bookRepo.getBooksOfCategoryBySort(selectedCategoryId, sort);
			bookWrapper.setBooks(books);
			bookWrapper.setTotal_count(Long.valueOf(books.size()));
			return bookWrapper;
		}
		Page<Book> page = bookRepo.getBooksOfCategory(selectedCategoryId, Pageable.unpaged());
		bookWrapper.setBooks(page.toList());
		bookWrapper.setTotal_count(page.getTotalElements());
		return bookWrapper;


	}

	@Override
	public BookTransactionDto viewBorrowedBooks(Optional<java.util.Date> returnDate, Optional<String> searchString, Optional<Integer> pageNo,Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
		Pageable paging;
		Sort sort = null;
		Date bookReturnDate = null;


		BookTransactionDto bookTransactionWrapper = new BookTransactionDto();

		if(returnDate.isPresent()) { 
			bookReturnDate = new Date(returnDate.get().getTime());
		}
		if(sortByColumn.isPresent() && sortDirection.isPresent()) {
			if(sortDirection.get().equals("desc")) {
				sort = Sort.by(sortByColumn.get()).descending();
			} else {
				sort = Sort.by(sortByColumn.get()).ascending();
			}
		}

		if(pageNo.isPresent() && pageSize.isPresent()) {
			if(sort != null) {
				paging = PageRequest.of(pageNo.get(), pageSize.get(),sort);
			} else {
				paging = PageRequest.of(pageNo.get(), pageSize.get());
			}

			if(bookReturnDate != null) { //all threes present

				Page<BookTransaction> page = bookTransactionRepo.viewBorrowedBooksOnDateWithPagination(bookReturnDate, paging);
				bookTransactionWrapper.setBooks(page.toList());
				bookTransactionWrapper.setTotal_count(page.getTotalElements());
			
				return bookTransactionWrapper;
			}

			// page + sort / page
			Page<BookTransaction> page = bookTransactionRepo.viewBorrowedBooks(paging);
			bookTransactionWrapper.setBooks(page.toList());
			bookTransactionWrapper.setTotal_count(page.getTotalElements());
			
			return bookTransactionWrapper;
		}

		if(sort != null) {
			
			if(bookReturnDate != null) {  // sort + return date
				List<BookTransaction> books = bookTransactionRepo.viewBorrowedBooksOnDateWithSort(bookReturnDate, sort);
				bookTransactionWrapper.setBooks(books);
				bookTransactionWrapper.setTotal_count(Long.valueOf(books.size()));
				
				return bookTransactionWrapper;
			}
			
			//only sort
			List<BookTransaction> books = bookTransactionRepo.viewBorrowedBooksWithSort(sort);
			bookTransactionWrapper.setBooks(books);
			bookTransactionWrapper.setTotal_count(Long.valueOf(books.size()));
			
			return bookTransactionWrapper;
		}
		
		if(bookReturnDate != null) { // return date
			List<BookTransaction> books = bookTransactionRepo.viewBorrowedBooksOnDate(bookReturnDate);
			bookTransactionWrapper.setBooks(books);
			bookTransactionWrapper.setTotal_count(Long.valueOf(books.size()));
		
			return bookTransactionWrapper;
		}
		
		else {
			List<BookTransaction> books = bookTransactionRepo.viewBorrowedBooks();
			bookTransactionWrapper.setBooks(books);
			bookTransactionWrapper.setTotal_count(Long.valueOf(books.size()));
			
			return bookTransactionWrapper;
		}
		

	}

	@Override
	public List<Book> viewBooksByTitleSearch(String bookTitleSearchString, Integer selectedCategoryId) { 
		bookTitleSearchString = "%" + bookTitleSearchString.toLowerCase() + "%";
		return bookRepo.searchByBookTitle(bookTitleSearchString, selectedCategoryId);
	}

	@Override
	public List<Book> viewBooksByAuthorSearch(String bookAuthorSearchString, Integer selectedCategoryId) { 
		bookAuthorSearchString = "%" + bookAuthorSearchString.toLowerCase() + "%";
		return bookRepo.searchByBookAuthor(bookAuthorSearchString, selectedCategoryId);
	}

	@Override
	public List<Book> viewBooksByPublisherSearch(String bookPublisherSearchString, Integer selectedCategoryId) { 
		bookPublisherSearchString = "%" + bookPublisherSearchString.toLowerCase() + "%";
		return bookRepo.searchByBookPublisher(bookPublisherSearchString, selectedCategoryId);
	}

	@Override
	public List<Book> viewBooksByCategorySearch(String bookCategorySearchString, Integer selectedCategoryId) { 
		bookCategorySearchString = "%" + bookCategorySearchString.toLowerCase() + "%";
		return bookRepo.searchByBookCategory(bookCategorySearchString, selectedCategoryId);
	}

	@Override
	public List<Book> viewBooksByAllSearch(String bookAllSearchString, Integer selectedCategoryId){
		bookAllSearchString = "%" + bookAllSearchString.toLowerCase() + "%";
		return bookRepo.searchByAll(bookAllSearchString, selectedCategoryId);
	}

	@Override
	public BookTransaction depositBook(Integer transactionId) throws OpenBookLibraryException {
		BookTransaction bookTransaction = BookUtility.utilityObject.getBookTransactionFromTransactionId(transactionId, bookTransactionRepo);
		Date currentDate = new Date(System.currentTimeMillis());
		bookTransaction.setReturnDate(currentDate);
		bookTransaction.setBookStatus(BookTransactionStatus.RETURNED);
		bookTransaction = bookTransactionRepo.save(bookTransaction);
		Book borrowedBook = BookUtility.utilityObject.getBookFromBookId(bookTransaction.getBorrowedBook().getBookId(), bookRepo);
		Integer availableCopies = borrowedBook.getBookAvailableCopies();
		borrowedBook.setBookAvailableCopies(availableCopies + 1);
		if(availableCopies == 0 && !borrowedBook.getIsAvailable()) {
			borrowedBook.setIsAvailable(true);
			if(borrowedBook.getToNotifyUsers() != null && borrowedBook.getToNotifyUsers().size() > 0) {
				restTemplate.postForObject("http://library-mail-service/notify/book-available", borrowedBook, Object.class);
			
				borrowedBook.setToNotifyUsers(new ArrayList<User>());
			}
			
		}
		
		bookRepo.save(borrowedBook);
		
		restTemplate.postForObject("http://library-mail-service/notify/book-deposited", bookTransaction, Object.class);
		return bookTransaction;
	}


	@Override
	public BookAuthorApi viewAllAuthors(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
		Pageable paging;
		Sort sort = null;
		BookAuthorApi authorBookWrapper = new BookAuthorApi();
		if(sortByColumn.isPresent() && sortDirection.isPresent()) {
			if(sortDirection.get().equals("desc")) {
				sort = Sort.by(sortByColumn.get()).descending();
			} else {
				sort = Sort.by(sortByColumn.get()).ascending();
			}
		}

		if(pageNo.isPresent() && pageSize.isPresent()) {
			if(sort != null) {
				paging = PageRequest.of(pageNo.get(), pageSize.get(),sort);
			} else {
				paging = PageRequest.of(pageNo.get(), pageSize.get());
			}

			Page<BookAuthor> page = bookAuthorRepo.viewAuthors(paging);
			authorBookWrapper.setAuthors(page.toList());
			authorBookWrapper.setTotal_count(page.getTotalElements());
			return authorBookWrapper;
		}

		if(sort != null) {
			List<BookAuthor> authors = bookAuthorRepo.findAll(sort);
			authorBookWrapper.setAuthors(authors);
			authorBookWrapper.setTotal_count(Long.valueOf(authors.size()));
			return authorBookWrapper;
		}
		List<BookAuthor> authors = bookAuthorRepo.findAll();
		authorBookWrapper.setAuthors(authors);
		authorBookWrapper.setTotal_count(Long.valueOf(authors.size()));
		return authorBookWrapper;
	}
	
	@Override
	public BookCategoryApi viewAllCategories(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {

		Pageable paging;
		Sort sort = null;
		BookCategoryApi categoryBookWrapper = new BookCategoryApi();
		if(sortByColumn.isPresent() && sortDirection.isPresent()) {
			if(sortDirection.get().equals("desc")) {
				sort = Sort.by(sortByColumn.get()).descending();
			} else {
				sort = Sort.by(sortByColumn.get()).ascending();
			}
		}

		if(pageNo.isPresent() && pageSize.isPresent()) {
			if(sort != null) {
				paging = PageRequest.of(pageNo.get(), pageSize.get(),sort);
			} else {
				paging = PageRequest.of(pageNo.get(), pageSize.get());
			}

			Page<BookCategory> page = bookCategoryRepo.viewCategories(paging);
			categoryBookWrapper.setCategories(page.toList());
			categoryBookWrapper.setTotal_count(page.getTotalElements());
			return categoryBookWrapper;
		}

		if(sort != null) {
			List<BookCategory> categories = bookCategoryRepo.findAll(sort);
			categoryBookWrapper.setCategories(categories);
			categoryBookWrapper.setTotal_count(Long.valueOf(categories.size()));
			return categoryBookWrapper;
		}
		List<BookCategory> categories = bookCategoryRepo.findAll();
		categoryBookWrapper.setCategories(categories);
		categoryBookWrapper.setTotal_count(Long.valueOf(categories.size()));
		return categoryBookWrapper;

	}
	
	@Override
	public BookPublisherApi viewAllPublishers(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {

		Pageable paging;
		Sort sort = null;
		BookPublisherApi publisherBookWrapper = new BookPublisherApi();
		if(sortByColumn.isPresent() && sortDirection.isPresent()) {
			if(sortDirection.get().equals("desc")) {
				sort = Sort.by(sortByColumn.get()).descending();
			} else {
				sort = Sort.by(sortByColumn.get()).ascending();
			}
		}

		if(pageNo.isPresent() && pageSize.isPresent()) {
			if(sort != null) {
				paging = PageRequest.of(pageNo.get(), pageSize.get(),sort);
			} else {
				paging = PageRequest.of(pageNo.get(), pageSize.get());
			}

			Page<BookPublisher> page = bookPublisherRepo.viewPublishers(paging);
			publisherBookWrapper.setPublishers(page.toList());
			publisherBookWrapper.setTotal_count(page.getTotalElements());
			return publisherBookWrapper;
		}

		if(sort != null) {
			List<BookPublisher> publishers = bookPublisherRepo.findAll(sort);
			publisherBookWrapper.setPublishers(publishers);
			publisherBookWrapper.setTotal_count(Long.valueOf(publishers.size()));
			return publisherBookWrapper;
		}
		List<BookPublisher> publishers = bookPublisherRepo.findAll();
		publisherBookWrapper.setPublishers(publishers);
		publisherBookWrapper.setTotal_count(Long.valueOf(publishers.size()));
		return publisherBookWrapper;

	}

	@Override
	public BookCategory updateBookCategory(BookCategory category) throws OpenBookLibraryException {
		BookUtility.utilityObject.getCategoryFromCategoryId(category.getCategoryId(), bookCategoryRepo);
		category = bookCategoryRepo.save(category);
		return category;

	}

	@Override
	public BookAuthor updateBookAuthor(BookAuthor author) throws OpenBookLibraryException {
		BookUtility.utilityObject.getAuthorFromAuthorId(author.getBookAuthorId(), bookAuthorRepo);
		author = bookAuthorRepo.save(author);
		return author;
	}

	@Override
	public BookPublisher updateBookPublisher(BookPublisher publisher) throws OpenBookLibraryException {
		BookUtility.utilityObject.getPublisherFromPublisherId(publisher.getPublisherId(), bookPublisherRepo);
		publisher = bookPublisherRepo.save(publisher);
		return publisher;
	}

	@Override
	public List<BookTransaction> lostBook(Integer transactionId) throws OpenBookLibraryException {
		BookTransaction borrowedBook = BookUtility.utilityObject.getBookTransactionFromTransactionId(transactionId, bookTransactionRepo);
		borrowedBook.setBookStatus(BookTransactionStatus.LOST);
		bookTransactionRepo.save(borrowedBook);
		List<BookTransaction> borrowedLostBooks = bookTransactionRepo.getLostBooksByUser(borrowedBook.getBorrowedToUser().getUserId());
		return borrowedLostBooks;
	}

	@Override
	public List<Date> getBorrowedBooksDate() {
		return bookTransactionRepo.getBorrowedBooksDate();
	}
	
	
	@Override
	public InventoryReport getInventoryReport() {
		
		InventoryReport report = new InventoryReport();
		List<User> users = userRepo.findAll();
		Integer totalMembers = 0;
		for (User user : users) {
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				if(role.getName().equals("ROLE_USER")) {
					totalMembers += 1;
				}
			}
		}
		
		report.setTotalMembers(totalMembers);
		report.setTotalBooks(bookRepo.getTotalBooks());
		report.setBorrowedBooks(bookTransactionRepo.getTotalBorrowedBooks()); ;
		report.setTotalBooksLost(bookTransactionRepo.getTotalLostBooks());
		report.setTotalPriceOfBooks(bookRepo.getTotalPriceOfAvailableBooks());
		
		return report;
		 
		
	}
	
	@Override
	public List<TopBorrowedBooks> topBorrowedBooks(Optional<Integer> optionalLimit) {
		
			List<TopBorrowedBooks> topBooks = bookTransactionRepo.getTopBorrowedBooks();
			if(optionalLimit.isEmpty() || optionalLimit.get() <= 0 || optionalLimit.get() >= topBooks.size()) {
				return topBooks;
			}
			
			System.out.println(topBooks);
			return topBooks.subList(0, optionalLimit.get());
	}

	@Override
	public Boolean addUserInNotificationList(Integer bookId, Integer userId) throws OpenBookLibraryException {
		Book book = BookUtility.utilityObject.getBookFromBookId(bookId, bookRepo);
		User user = userRepo.findById(userId).get();
		List<User> toNotifyUsers = book.getToNotifyUsers();
		if(toNotifyUsers == null) {
			toNotifyUsers = new ArrayList<>();
		}
		
		for (User _user : toNotifyUsers) {
			if(_user.getUserId() == userId) {
				return true;
			}
		}
		toNotifyUsers.add(user);
		book.setToNotifyUsers(toNotifyUsers);
		book = bookRepo.save(book);
		return true;
	}

	@Override
	public Book getBookById(Integer bookId) throws OpenBookLibraryException {
		return BookUtility.utilityObject.getBookFromBookId(bookId, bookRepo);
	}

	@Override
	public BookDto getBooksOfAuthor(Integer authorId,Optional<Integer> pageNo, Optional<Integer> pageSize) {
		Pageable paging;
		if(pageNo.isPresent() && pageSize.isPresent()) {
			paging = PageRequest.of(pageNo.get(), pageSize.get());
		} else  {
			paging = Pageable.unpaged();
		}
		BookDto bookDto = new BookDto();
		Page<Book> booksPages = bookRepo.getBooksOfAuthor(authorId, paging);
		bookDto.setBooks(booksPages.toList());
		bookDto.setTotal_count(booksPages.getTotalElements());
		return bookDto;
	}
	
	@Override
	public BookDto getBooksOfCategory(Integer categoryId, Optional<Integer> pageNo, Optional<Integer> pageSize) {
		
		Pageable paging;
		if(pageNo.isPresent() && pageSize.isPresent()) {
			paging = PageRequest.of(pageNo.get(), pageSize.get());
		} else  {
			paging = Pageable.unpaged();
		}
		
		BookDto bookDto = new BookDto();
		Page<Book> booksPages = bookRepo.getBooksOfCategory(categoryId, paging);
		bookDto.setBooks(booksPages.toList());
		bookDto.setTotal_count(booksPages.getTotalElements());
		return bookDto;
	}


}
