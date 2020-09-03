package com.obl.book.services;

import java.sql.Date;
import java.util.List;

import com.obl.book.exceptions.OpenBookLibraryException;
import com.obl.book.models.Book;
import com.obl.book.models.BookAuthor;
import com.obl.book.models.BookCategory;
import com.obl.book.models.BookPublisher;
import com.obl.book.models.IssueBook;
import com.obl.book.models.common.User;


public interface BookService {

	
	public List<Book> viewBooks();

	public IssueBook issueBook(Integer bookId, Integer issuedToUserId) throws OpenBookLibraryException;
	
	public BookCategory addBookCategory(BookCategory category) throws OpenBookLibraryException;
	 
	public BookAuthor addAuthor(BookAuthor author) throws OpenBookLibraryException;
	
	public BookPublisher addPublisher(BookPublisher publisher) throws OpenBookLibraryException;
	
	public Book addBook(Book book) throws OpenBookLibraryException;
	
	public Book updateBook(Book book) throws OpenBookLibraryException;
	
	public User checkIfUserIsAuthenticated(String emailId, String password) throws OpenBookLibraryException;

}
