package com.obl.book.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.obl.book.exceptions.OpenBookLibraryException;
import com.obl.book.models.Book;
import com.obl.book.models.BookAuthor;
import com.obl.book.models.BookCategory;
import com.obl.book.models.BookPublisher;
import com.obl.book.models.IssueBook;
import com.obl.book.models.common.User;
import com.obl.book.services.BookService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/books")
public class LibraryBookController {
	
	@Autowired
	BookService bookService;

	
	@GetMapping("/view/allBooks")
	public List<Book> viewBooks() {
		
		return bookService.viewBooks();
	}
	

	@PostMapping("/issueBook/{bookId}") 
	public IssueBook issueBook(@PathVariable Integer bookId, @RequestHeader Map<String, String> authHeader) throws OpenBookLibraryException{
		
		System.out.println(authHeader);
		String emailId = authHeader.get("emailid");
		String password = authHeader.get("password");
		User user = bookService.checkIfUserIsAuthenticated(emailId, password);
		return bookService.issueBook(bookId, user.getUserId());
	}
	

	@PostMapping("/add/book")
	public Book addBook(@RequestBody Book book) throws OpenBookLibraryException {
		return bookService.addBook(book);
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
	
	
}
