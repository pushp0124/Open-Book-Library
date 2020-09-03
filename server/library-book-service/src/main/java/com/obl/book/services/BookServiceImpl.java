package com.obl.book.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.obl.book.exceptions.OpenBookLibraryException;
import com.obl.book.models.Book;
import com.obl.book.models.BookAuthor;
import com.obl.book.models.BookCategory;
import com.obl.book.models.BookPublisher;
import com.obl.book.models.IssueBook;
import com.obl.book.models.common.User;
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
	
	
	public List<Book> viewBooks() {
		
		return bookRepo.findAll();
		
	}

	public IssueBook issueBook(Integer bookId, Integer issuedToUserId) throws OpenBookLibraryException {
		
		//check if book is already booked, availableCopies are zero and throw exception if booked
		
		User issuedToUser = restTemplate.getForObject("http://library-auth-service/auth/user/" + issuedToUserId, User.class);
		Book issuedBook = BookUtility.utilityObject.getBookFromBookId(bookId, bookRepo);
		System.out.println(issuedToUser);
		IssueBook issueBook = new IssueBook();
		Long millis=System.currentTimeMillis();
		Date currentDate = new Date(millis);
		issueBook.setIssueDate(currentDate);
		issueBook.setIssuedToUser(issuedToUser);
		issueBook.setIssuedBook(issuedBook);
		
		Date returnDate = Date.valueOf(currentDate.toLocalDate().plusDays(bookIssuedDays));
		issueBook.setReturnDate(returnDate);
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

	public Book addBook(Book book) throws OpenBookLibraryException {
		book = bookRepo.save(book);
		return book;
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
	
}
