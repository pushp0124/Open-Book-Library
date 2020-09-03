package com.obl.book.utils;

import java.util.Optional;

import com.obl.book.exceptions.OpenBookLibraryException;
import com.obl.book.models.Book;
import com.obl.book.models.BookAuthor;
import com.obl.book.models.BookCategory;
import com.obl.book.models.BookPublisher;
import com.obl.book.models.IssueBook;
import com.obl.book.repos.BookAuthorRepo;
import com.obl.book.repos.BookCategoryRepo;
import com.obl.book.repos.BookPublisherRepo;
import com.obl.book.repos.BookRepo;
import com.obl.book.repos.IssueBookRepo;

public class BookUtility {
	
public static final BookUtility utilityObject = new BookUtility();
	
	public static final String dateTimeFormatter = "yyyy-MM-dd";

	
	public BookCategory getCategoryFromCategoryId(Integer categoryId, BookCategoryRepo categoryRepo) throws OpenBookLibraryException {
		Optional<BookCategory> optionalCategory = categoryRepo.findById(categoryId);
		if(optionalCategory.isEmpty()) {
			throw new OpenBookLibraryException("Book Category with Category Id " +  categoryId + " doesn't exist");
		}
		return optionalCategory.get();
	}
	
	public BookAuthor getAuthorFromAuthorId(Integer authorId, BookAuthorRepo authorRepo) throws OpenBookLibraryException {
		Optional<BookAuthor> optionalAuthor = authorRepo.findById(authorId);
		if(optionalAuthor.isEmpty()) {
			throw new OpenBookLibraryException("Author with Author Id " +  authorId + " doesn't exist");
		}
		return optionalAuthor.get();
	}

	public BookPublisher getPublisherFromPublisherId(Integer publisherId, BookPublisherRepo publisherRepo) throws OpenBookLibraryException {
		Optional<BookPublisher> optionalPublisher = publisherRepo.findById(publisherId);
		if(optionalPublisher.isEmpty()) {
			throw new OpenBookLibraryException("Publisher with PublisherId " +  publisherId + " doesn't exist");
		}
		return optionalPublisher.get();
	}
	
	public Book getBookFromBookId(Integer bookId, BookRepo bookRepo) throws OpenBookLibraryException {
		Optional<Book> optionalBook = bookRepo.findById(bookId);
		if(optionalBook.isEmpty()) {
			throw new OpenBookLibraryException("Book with BookId " +  bookId + " doesn't exist");
		}

		return optionalBook.get();
	}

	public IssueBook getIssueBookFromIssueId(Integer issueId, IssueBookRepo issueBookRepo) throws OpenBookLibraryException {

		Optional<IssueBook> optionalIssue = issueBookRepo.findById(issueId);
		if(optionalIssue.isEmpty()) {
			throw new OpenBookLibraryException("Book with issueId " +  issueId + " is not issued");
		}
		return optionalIssue.get();
	}


}
