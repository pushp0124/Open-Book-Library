package com.obl.book.services;

import java.util.List;
import java.util.Optional;

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


public interface BookService {

	
	public List<Book> viewAllBooks();

	public IssueBookApi viewIssuedBooks(Integer userId, Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public IssueBookApi viewIssuedBooks(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookAuthorApi viewAllAuthor(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookCategoryApi viewAllCategory(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookPublisherApi viewAllPublisher(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	
	public IssueBook issueBook(Integer bookId, Integer issuedToUserId) throws OpenBookLibraryException;
	
	public BookCategory addBookCategory(BookCategory category) throws OpenBookLibraryException;
	 
	public BookAuthor addAuthor(BookAuthor author) throws OpenBookLibraryException;
	
	public BookPublisher addPublisher(BookPublisher publisher) throws OpenBookLibraryException;
	
	public Boolean addBooks(List<Book> books) throws OpenBookLibraryException;
	
	public Book updateBook(Book book) throws OpenBookLibraryException;
	
	public User checkIfUserIsAuthenticated(String emailId, String password) throws OpenBookLibraryException;

	public List<Book> viewBooksByTitleSearch(String bookTitleSearchString);
	
	public List<Book> viewBooksByAuthorSearch(String bookAuthorSearchString);
	
	public List<Book> viewBooksByPublisherSearch(String bookPublisherSearchString);
	
	public List<Book> viewBooksByCategorySearch(String bookCategorySearchString);
	
	public List<Book> viewBooksByAllSearch(String bookAllSearchString); //title + author + publisher + category
	
	public IssueBook depositBook(Integer issueId) throws OpenBookLibraryException;
	
	
}
