package com.obl.book.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.obl.book.services.BookService;

@RestController
@CrossOrigin("http://localhost:4200")
public class LibraryBookController {
	
	@Autowired
	BookService bookService;


	@PostMapping("/borrowBook/{bookId}/{userId}/{borrowDate}") 
	public BookTransaction borrowBook(@PathVariable Integer bookId, @PathVariable Integer userId,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date borrowDate) throws OpenBookLibraryException{
		return bookService.borrowBook(bookId, userId, borrowDate);
	}
	

	@GetMapping("/borrowedBooks/{userId}")
	public BookTransactionDto viewBorrowedBooks(@PathVariable Integer userId,@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewBorrowedBooks(userId,pageNo,pageSize, sortByColumn, sortDirection);
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
	
	@PutMapping("/update/category")
	public BookCategory updateBookCategory(@RequestBody BookCategory category) throws OpenBookLibraryException {
		return bookService.updateBookCategory(category);
	}
	
	
	@PutMapping("/update/author")
	public BookAuthor updateBookAuthor(@RequestBody BookAuthor author) throws OpenBookLibraryException {
		return bookService.updateBookAuthor(author);
	}
	
	@PutMapping("/update/publisher")
	public BookPublisher updateBookPublisher(@RequestBody BookPublisher publisher) throws OpenBookLibraryException {
		return bookService.updateBookPublisher(publisher);
	}

	@GetMapping("/search/title/{bookTitleSearchString}/{selectedCategoryId}")
	public List<Book> viewBooksByTitleSearch(@PathVariable String bookTitleSearchString, @PathVariable Integer selectedCategoryId) {
		return bookService.viewBooksByTitleSearch(bookTitleSearchString, selectedCategoryId);
	}
	
	
	@GetMapping("/search/category/{bookCategorySearchString}/{selectedCategoryId}")
	public List<Book> viewBooksByCategorySearch(@PathVariable String bookCategorySearchString, @PathVariable Integer selectedCategoryId) {
		return bookService.viewBooksByCategorySearch(bookCategorySearchString, selectedCategoryId);
	}
	
	
	@GetMapping("/search/author/{bookAuthorSearchString}/{selectedCategoryId}")
	public List<Book> viewBooksByAuthorSearch(@PathVariable String bookAuthorSearchString, @PathVariable Integer selectedCategoryId) {
		return bookService.viewBooksByAuthorSearch(bookAuthorSearchString, selectedCategoryId);
	}
	
	
	@GetMapping("/search/publisher/{bookPublisherSearchString}/{selectedCategoryId}")
	public List<Book> viewBooksByPublisherSearch(@PathVariable String bookPublisherSearchString,  @PathVariable Integer selectedCategoryId) {
		return bookService.viewBooksByPublisherSearch(bookPublisherSearchString, selectedCategoryId);
	}
	
	
	@GetMapping("/search/all/{bookAllSearchString}/{selectedCategoryId}")
	public List<Book> viewBooksByAllSearch(@PathVariable String bookAllSearchString, @PathVariable Integer selectedCategoryId) {
		return bookService.viewBooksByAllSearch(bookAllSearchString,selectedCategoryId );
	}
	
	@PutMapping("/depositBook/{issueId}")
	public BookTransaction depositIssuedBook(@PathVariable Integer issueId) throws OpenBookLibraryException {
		return bookService.depositBook(issueId);
	}
	
	@PutMapping("/lostBook/{issueId}")
	public List<BookTransaction> lostIssuedBook(@PathVariable Integer issueId) throws OpenBookLibraryException {
		return bookService.lostBook(issueId);
	}
	
	@GetMapping("/borrowedBooks")
	public BookTransactionDto viewBorrowedBooks(@RequestParam("returnDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<java.util.Date> returnDate,  @RequestParam("searchString") Optional<String> searchString, @RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewBorrowedBooks(returnDate, searchString, pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	
	@GetMapping("/get/all/categories")
	public BookCategoryApi viewAllCategories(@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewAllCategories(pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	@GetMapping("/get/all/authors")
	public BookAuthorApi viewAllAuthors(@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewAllAuthors(pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	@GetMapping("/get/all/publishers")
	public BookPublisherApi viewAllPublishers(@RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		return bookService.viewAllPublishers(pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	@GetMapping("/view/all/books/{selectedCategoryId}")
	public BookDto viewAllBooks(@PathVariable Integer selectedCategoryId, @RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("sortByColumn") Optional<String> sortByColumn,@RequestParam(name="sortDirection", defaultValue="asc") Optional<String> sortDirection) {
		
		return bookService.viewAllBooks(selectedCategoryId,  pageNo,pageSize, sortByColumn, sortDirection);
	}
	
	
	@GetMapping("/get/borrowedBooksDate")
	public List<Date> getBorrowedBooksDate() {
		
		return bookService.getBorrowedBooksDate();
	}
	
	@GetMapping("/get/inventoryReport")
	public InventoryReport getInventoryReport() {
		
		return bookService.getInventoryReport();
	}
	
	@GetMapping("/get/topBorrowedBooks")
	public List<TopBorrowedBooks> getTopBorrowedBooks(@RequestParam("limit") Optional<Integer> limit) {
		
		return bookService.topBorrowedBooks(limit);
	}
	
	@PutMapping("/add/in-notification-list/{userId}/{bookId}")
	public Boolean addUserInNotificationList(@PathVariable Integer bookId, @PathVariable Integer userId) throws OpenBookLibraryException {
		return bookService.addUserInNotificationList(bookId, userId);
	}
	
	@GetMapping("/get/bookDetail/{bookId}")
	public Book getBookById(@PathVariable Integer bookId) throws OpenBookLibraryException {
		return bookService.getBookById(bookId);
	}
	
	@GetMapping("/get/books/ofAuthor/{authorId}")
	public BookDto getBooksOfAuthor(@PathVariable Integer authorId, Optional<Integer> pageNo, Optional<Integer> pageSize) {
		return bookService.getBooksOfAuthor(authorId, pageNo, pageSize);
	}
	
	@GetMapping("/get/books/ofCategory/{categoryId}")
	public BookDto getBooksOfCatgeory(@PathVariable Integer categoryId, @RequestParam("pageNo") Optional<Integer> pageNo, @RequestParam("pageSize") Optional<Integer> pageSize) {
		return bookService.getBooksOfCategory(categoryId, pageNo, pageSize);
	}
}
