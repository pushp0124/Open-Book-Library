package com.obl.book.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Locale.Category;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.obl.book.services.BookService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/books")
public class LibraryBookController {
	
	@Autowired
	BookService bookService;

	
	@GetMapping("/view/allBooks")
	public List<Book> viewAllBooks() {
		
		return bookService.viewAllBooks();
	}
	

	@PostMapping("/issueBook/{bookId}") 
	public IssueBook issueBook(@PathVariable Integer bookId, @RequestHeader Map<String, String> authHeader) throws OpenBookLibraryException{
		
		System.out.println(authHeader);
		String emailId = authHeader.get("emailid");
		String password = authHeader.get("password");
		User user = bookService.checkIfUserIsAuthenticated(emailId, password);
		return bookService.issueBook(bookId, user.getUserId());
	}
	

	@GetMapping("/issuedBooks/{userId}")
	public IssueBookApi viewIssuedBooks(@PathVariable Integer userId,@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewIssuedBooks(userId,pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	@PostMapping("/add/books")
	public Boolean addBook(@RequestBody List<Book> books) throws OpenBookLibraryException {
		return bookService.addBooks(books);
	}


	@PostMapping("/add/category")
	public BookCategory addBookCategory(@RequestBody BookCategory category) throws OpenBookLibraryException {
		return bookService.addBookCategory(category);
	}
	
	@PostMapping("/add/author")
	public BookAuthor addAuthor(@RequestBody BookAuthor author) throws OpenBookLibraryException {
		return bookService.addAuthor(author);
	}
	
	@PostMapping("/add/publisher")
	public BookPublisher addPublisher(@RequestBody BookPublisher publisher) throws OpenBookLibraryException {
		return bookService.addPublisher(publisher);
	}
	
	@PutMapping("/update/book")
	public Book updateBook(@RequestBody Book book) throws OpenBookLibraryException {
		return bookService.updateBook(book);
	}
	
	
	
	@GetMapping("/search/title/{bookTitleSearchString}")
	public List<Book> viewBooksByTitleSearch(@PathVariable String bookTitleSearchString) {
		return bookService.viewBooksByTitleSearch(bookTitleSearchString);
	}
	
	
	@GetMapping("/search/category/{bookCategorySearchString}")
	public List<Book> viewBooksByCategorySearch(@PathVariable String bookCategorySearchString) {
		return bookService.viewBooksByCategorySearch(bookCategorySearchString);
	}
	
	
	@GetMapping("/search/author/{bookAuthorSearchString}")
	public List<Book> viewBooksByAuthorSearch(@PathVariable String bookAuthorSearchString) {
		return bookService.viewBooksByAuthorSearch(bookAuthorSearchString);
	}
	
	
	@GetMapping("/search/publisher/{bookPublisherSearchString}")
	public List<Book> viewBooksByPublisherSearch(@PathVariable String bookPublisherSearchString) {
		return bookService.viewBooksByPublisherSearch(bookPublisherSearchString);
	}
	
	
	@GetMapping("/search/all/{bookAllSearchString}")
	public List<Book> viewBooksByAllSearch(@PathVariable String bookAllSearchString) {
		return bookService.viewBooksByAllSearch(bookAllSearchString);
	}
	
	@PutMapping("/depositBook/{issueId}")
	public IssueBook depositIssuedBook(@PathVariable Integer issueId) throws OpenBookLibraryException {
		return bookService.depositBook(issueId);
	}
	
	@GetMapping("/issuedBooks")
	public IssueBookApi viewIssuedBooks(@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewIssuedBooks(pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	
	@GetMapping("/get/all/category")
	public BookCategoryApi viewAllCategory(@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewAllCategory(pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	@GetMapping("/get/all/authors")
	public BookAuthorApi viewAllAuthor(@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewAllAuthor(pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	@GetMapping("/get/all/publishers")
	public BookPublisherApi viewAllPublisher(@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewAllPublisher(pageNo,pageSize, sortByColumn, sortDirection);
	}
	
}
