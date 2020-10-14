package com.obl.book.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
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

public interface BookService {

	
	public BookDto viewAllBooks(Integer selectedCategoryId, Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookTransactionDto viewBorrowedBooks(Integer userId, Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookTransactionDto viewBorrowedBooks(Optional<java.util.Date> returnDate, Optional<String> searchString, Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookAuthorApi viewAllAuthors(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookCategoryApi viewAllCategories(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookPublisherApi viewAllPublishers(Optional<Integer> pageNo, Optional<Integer> pageSize, Optional<String> sortByColumn, Optional<String> sortDirection);
	
	public BookTransaction borrowBook(Integer bookId, Integer borrowdToUserId, java.util.Date borrowDate) throws OpenBookLibraryException;
	
	public BookCategory addBookCategory(BookCategory category) throws OpenBookLibraryException;
	 
	public BookAuthor addAuthor(BookAuthor author) throws OpenBookLibraryException;
	
	public BookPublisher addPublisher(BookPublisher publisher) throws OpenBookLibraryException;
	
	public Boolean addBooks(List<Book> books) throws OpenBookLibraryException;
	
	public Book updateBook(Book book) throws OpenBookLibraryException;
	
	public BookCategory updateBookCategory(BookCategory category) throws OpenBookLibraryException;
	
	public BookAuthor updateBookAuthor(BookAuthor author) throws OpenBookLibraryException;
	
	public BookPublisher updateBookPublisher(BookPublisher publisher) throws OpenBookLibraryException;
	
	public List<Book> viewBooksByTitleSearch(String bookTitleSearchString, Integer selectedCategoryId);
	
	public List<Book> viewBooksByAuthorSearch(String bookAuthorSearchString,Integer selectedCategoryId);
	
	public List<Book> viewBooksByPublisherSearch(String bookPublisherSearchString,Integer selectedCategoryId);
	
	public List<Book> viewBooksByCategorySearch(String bookCategorySearchString, Integer selectedCategoryId);
	
	public List<Book> viewBooksByAllSearch(String bookAllSearchString,Integer selectedCategoryId); //title + author + publisher + category
	
	public BookTransaction depositBook(Integer borrowId) throws OpenBookLibraryException;
	
	public List<BookTransaction> lostBook(Integer borrowId) throws OpenBookLibraryException;
	
	public List<Date> getBorrowedBooksDate();
	
	public InventoryReport getInventoryReport();
	
	public List<TopBorrowedBooks> topBorrowedBooks(Optional<Integer> limit);
	
	public Boolean addUserInNotificationList(Integer bookId, Integer userId) throws OpenBookLibraryException;
	
	public Book getBookById(Integer bookId) throws OpenBookLibraryException;
	
	public BookDto getBooksOfAuthor(Integer authorId, Optional<Integer> pageNo, Optional<Integer> pageSize);
	
	public BookDto getBooksOfCategory(Integer categoryId, Optional<Integer> pageNo, Optional<Integer> pageSize);

}
