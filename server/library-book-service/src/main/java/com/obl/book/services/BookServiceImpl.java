package com.obl.book.services;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.obl.book.exceptions.OpenBookLibraryException;
import com.obl.book.models.Book;
import com.obl.book.models.BookAuthor;
import com.obl.book.models.BookAuthorApi;
import com.obl.book.models.BookCategory;
import com.obl.book.models.BookCategoryApi;
import com.obl.book.models.BookPublisher;
import com.obl.book.models.BookPublisherApi;
import com.obl.book.models.IssueBook;
import com.obl.book.models.IssueBookApi;
import com.obl.book.models.common.User;
import com.obl.book.models.enums.IssueBookStatus;
import com.obl.book.repos.BookAuthorRepo;
import com.obl.book.repos.BookCategoryRepo;
import com.obl.book.repos.BookPublisherRepo;
import com.obl.book.repos.BookRepo;
import com.obl.book.repos.IssueBookRepo;
import com.obl.book.utils.BookUtility;


@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepo bookRepo;

	@Autowired
	IssueBookRepo issueBookRepo;

	@Autowired
	BookAuthorRepo bookAuthorRepo;

	@Autowired
	BookPublisherRepo bookPublisherRepo;

	@Autowired
	BookCategoryRepo bookCategoryRepo;

	@Autowired
	RestTemplate restTemplate;


	private Integer bookIssuedDays = 13;


	public List<Book> viewAllBooks() {

		return bookRepo.findAll();

	}

	public IssueBook issueBook(Integer bookId, Integer issuedToUserId) throws OpenBookLibraryException {

		//check if book is already booked, availableCopies are zero and throw exception if booked

		User issuedToUser = restTemplate.getForObject("http://library-auth-service/auth/user/" + issuedToUserId, User.class);
		Book issuedBook = BookUtility.utilityObject.getBookFromBookId(bookId, bookRepo);
		IssueBook issueBook = new IssueBook();
		Long millis=System.currentTimeMillis();
		Date currentDate = new Date(millis);
		issueBook.setIssueDate(currentDate);
		issueBook.setIssuedToUser(issuedToUser);
		issueBook.setIssuedBook(issuedBook);

		Date returnDate = Date.valueOf(currentDate.toLocalDate().plusDays(bookIssuedDays));
		issueBook.setReturnDate(returnDate);
		issueBook.setBookStatus(IssueBookStatus.ISSUED);
		Integer currentCopies = issuedBook.getBookAvailableCopies();

		if(currentCopies == 0) {

			issuedBook.setIsAvailable(false);
			throw new OpenBookLibraryException("Book is not available");

		}
		issuedBook.setBookAvailableCopies(currentCopies - 1);
		if (issuedBook.getBookAvailableCopies() == 0) {
			issuedBook.setIsAvailable(false);
		}
		issueBook = issueBookRepo.save(issueBook);
		return issueBook;
	}

	public BookCategory addBookCategory(BookCategory category) throws OpenBookLibraryException {

		category = bookCategoryRepo.save(category);
		return category;
	}

	public BookAuthor addAuthor(BookAuthor author) throws OpenBookLibraryException {
		author = bookAuthorRepo.save(author);
		return author;
	}

	public BookPublisher addPublisher(BookPublisher publisher) throws OpenBookLibraryException {
		publisher = bookPublisherRepo.save(publisher);
		return publisher;
	}

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

	public Book updateBook(Book book) throws OpenBookLibraryException {
		Optional<Book> optionalBook = bookRepo.findById(book.getBookId());
		if(optionalBook.isEmpty()) {
			throw new OpenBookLibraryException("Book with " + book.getBookId()  + " for updation doesn't exist");
		}
		book = bookRepo.save(book);
		return book;

	}

	public User checkIfUserIsAuthenticated(String emailId, String password) throws OpenBookLibraryException {
		return restTemplate.postForObject("http://library-auth-service/auth/login/" + emailId + "/" + password,null,User.class);

	}

	public IssueBookApi viewIssuedBooks(Integer userId,Optional<Integer> pageNo,Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
		Pageable paging;
		Sort sort = null;
		IssueBookApi issueBookWrapper = new IssueBookApi();
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
			Page<IssueBook> page = issueBookRepo.viewBooksIssuedById(userId,paging);
			issueBookWrapper.setBooks(page.toList());
			issueBookWrapper.setTotal_count(page.getTotalElements());
			return issueBookWrapper;
		}

		if(sort != null) {
			List<IssueBook> books = issueBookRepo.findAll(sort);
			issueBookWrapper.setBooks(books);
			issueBookWrapper.setTotal_count(Long.valueOf(books.size()));
			return issueBookWrapper;
		}
		List<IssueBook> books = issueBookRepo.findAll();
		issueBookWrapper.setBooks(books);
		issueBookWrapper.setTotal_count(Long.valueOf(books.size()));
		return issueBookWrapper;

	}
	
	
	public IssueBookApi viewIssuedBooks(Optional<Integer> pageNo,Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
		Pageable paging;
		Sort sort = null;
		IssueBookApi issueBookWrapper = new IssueBookApi();
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

			Page<IssueBook> page = issueBookRepo.viewIssuedBooks(paging);
			issueBookWrapper.setBooks(page.toList());
			issueBookWrapper.setTotal_count(page.getTotalElements());
			return issueBookWrapper;
		}

		if(sort != null) {
			List<IssueBook> books = issueBookRepo.findAll(sort);
			issueBookWrapper.setBooks(books);
			issueBookWrapper.setTotal_count(Long.valueOf(books.size()));
			return issueBookWrapper;
		}
		List<IssueBook> books = issueBookRepo.findAll();
		issueBookWrapper.setBooks(books);
		issueBookWrapper.setTotal_count(Long.valueOf(books.size()));
		return issueBookWrapper;

	}

	public List<Book> viewBooksByTitleSearch(String bookTitleSearchString) { 
		bookTitleSearchString = "%" + bookTitleSearchString.toLowerCase() + "%";
		return bookRepo.searchByBookTitle(bookTitleSearchString);
	}

	public List<Book> viewBooksByAuthorSearch(String bookAuthorSearchString) { 
		bookAuthorSearchString = "%" + bookAuthorSearchString.toLowerCase() + "%";
		return bookRepo.searchByBookAuthor(bookAuthorSearchString);
	}

	public List<Book> viewBooksByPublisherSearch(String bookPublisherSearchString) { 
		bookPublisherSearchString = "%" + bookPublisherSearchString.toLowerCase() + "%";
		return bookRepo.searchByBookPublisher(bookPublisherSearchString);
	}

	public List<Book> viewBooksByCategorySearch(String bookCategorySearchString) { 
		bookCategorySearchString = "%" + bookCategorySearchString.toLowerCase() + "%";
		return bookRepo.searchByBookCategory(bookCategorySearchString);
	}

	public List<Book> viewBooksByAllSearch(String bookAllSearchString){
		bookAllSearchString = "%" + bookAllSearchString.toLowerCase() + "%";
		return bookRepo.searchByAll(bookAllSearchString);
	}

	public IssueBook depositBook(Integer issueId) throws OpenBookLibraryException {
		IssueBook issueBook = BookUtility.utilityObject.getIssueBookFromIssueId(issueId, issueBookRepo);
		Date currentDate = new Date(System.currentTimeMillis());
		issueBook.setReturnDate(currentDate);
		issueBook.setBookStatus(IssueBookStatus.RETURNED);
		issueBook = issueBookRepo.save(issueBook);
		return issueBook;
	}

	
	public BookAuthorApi viewAllAuthor(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
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
	
	public BookCategoryApi viewAllCategory(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
		
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
	
	public BookPublisherApi viewAllPublisher(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection) {
		
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
	


}
