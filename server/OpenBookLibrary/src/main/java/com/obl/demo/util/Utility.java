package com.obl.demo.util;

import java.util.Optional;

import com.obl.demo.bean.Book;
import com.obl.demo.bean.BookAuthor;
import com.obl.demo.bean.BookCategory;
import com.obl.demo.bean.BookPublisher;
import com.obl.demo.bean.Credential;
import com.obl.demo.bean.IssueBook;
import com.obl.demo.bean.User;
import com.obl.demo.dao.BookAuthorDao;
import com.obl.demo.dao.BookCategoryDao;
import com.obl.demo.dao.BookDao;
import com.obl.demo.dao.BookPublisherDao;
import com.obl.demo.dao.CredentialDao;
import com.obl.demo.dao.IssueBookDao;
import com.obl.demo.dao.UserDao;
import com.obl.demo.exception.OpenBookLibraryException;

public class Utility {

public static final Utility utilityObject = new Utility();
	
	public static final String dateTimeFormatter = "yyyy-MM-dd";

	public User getUserFromUserId(Integer userId, UserDao userRepo) throws OpenBookLibraryException {
		Optional<User> optionalUserObj = userRepo.findById(userId);
		if(optionalUserObj.isEmpty()) {
			throw new OpenBookLibraryException("Account with UserId " + userId + " doesn't exist for User");
		} 
		return optionalUserObj.get();
	}
	
	public User getUserFromMailId(String mailId, UserDao userRepo) throws OpenBookLibraryException {
		Optional<User> optionalUserObj = userRepo.findByMailId(mailId);
		if(optionalUserObj.isEmpty()) {
			throw new OpenBookLibraryException("Account with mail Id " + mailId + " doesn't exist for User");
		} 
		return optionalUserObj.get();
	}

	public Credential getCredentialFromMailId(String mailId, CredentialDao credentialRepo) throws OpenBookLibraryException {
		Optional<Credential> optionalCredentialObj = credentialRepo.findByMailId(mailId);
		if(optionalCredentialObj.isEmpty()) {
			throw new OpenBookLibraryException("Account with" + mailId + " doesn't exist for User");
		} 
		return optionalCredentialObj.get();
	}

	public BookCategory getCategoryFromCategoryId(Integer categoryId, BookCategoryDao categoryRepo) throws OpenBookLibraryException {
		Optional<BookCategory> optionalCategory = categoryRepo.findById(categoryId);
		if(optionalCategory.isEmpty()) {
			throw new OpenBookLibraryException("Book Category with Category Id " +  categoryId + " doesn't exist");
		}
		return optionalCategory.get();
	}
	
	public BookAuthor getAuthorFromAuthorId(Integer authorId, BookAuthorDao authorRepo) throws OpenBookLibraryException {
		Optional<BookAuthor> optionalAuthor = authorRepo.findById(authorId);
		if(optionalAuthor.isEmpty()) {
			throw new OpenBookLibraryException("Author with Author Id " +  authorId + " doesn't exist");
		}
		return optionalAuthor.get();
	}

	public BookPublisher getPublisherFromPublisherId(Integer publisherId, BookPublisherDao publisherRepo) throws OpenBookLibraryException {
		Optional<BookPublisher> optionalPublisher = publisherRepo.findById(publisherId);
		if(optionalPublisher.isEmpty()) {
			throw new OpenBookLibraryException("Publisher with PublisherId " +  publisherId + " doesn't exist");
		}
		return optionalPublisher.get();
	}
	
	public Book getBookFromBookId(Integer bookId, BookDao bookRepo) throws OpenBookLibraryException {
		Optional<Book> optionalBook = bookRepo.findById(bookId);
		if(optionalBook.isEmpty()) {
			throw new OpenBookLibraryException("Book with BookId " +  bookId + " doesn't exist");
		}

		return optionalBook.get();
	}

	public IssueBook getIssueBookFromIssueId(Integer issueId, IssueBookDao issueBookRepo) throws OpenBookLibraryException {

		Optional<IssueBook> optionalIssue = issueBookRepo.findById(issueId);
		if(optionalIssue.isEmpty()) {
			throw new OpenBookLibraryException("Book with issueId " +  issueId + " is not issued");
		}
		return optionalIssue.get();
	}

}
