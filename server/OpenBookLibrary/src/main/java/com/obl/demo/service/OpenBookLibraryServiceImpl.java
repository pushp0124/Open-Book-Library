package com.obl.demo.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.obl.demo.util.Utility;

@Service
public class OpenBookLibraryServiceImpl implements OpenBookLibraryUserService, OpenBookLibraryAdminService {

	
	@Autowired
	UserDao userRepo;

	@Autowired
	CredentialDao credentialRepo;
	
	@Autowired
	BookDao bookRepo;
	
	@Autowired
	IssueBookDao issueBookRepo;

	@Autowired
	BookAuthorDao bookAuthorRepo;

	@Autowired
	BookPublisherDao bookPublisherRepo;

	@Autowired
	BookCategoryDao bookCategoryRepo;

	

	public User updatePassword(String phoneNo, String newPassword) throws OpenBookLibraryException {
		
		return null;
	}

	public User areCredentialsMatched(String mailId, String password) throws OpenBookLibraryException {
		
		try {
			Optional<Credential> optionalCredential = credentialRepo.findByMailId(mailId);
			if (optionalCredential.isPresent()) {
				Credential credential = optionalCredential.get();
				String hashUserPassword = HashAlgorithmService.hashedPassword(password, credential.getSaltArray());
				if (hashUserPassword.equals(credential.getHashedPassword())) {
					return userRepo.findByMailId(mailId).get();
				}
				throw new OpenBookLibraryException("Password mismatch");

			} else {
				throw new OpenBookLibraryException("Mail Id is not registered!");
			}

		} catch (Exception exception) {
			throw new OpenBookLibraryException(exception.getMessage());

		}
	}

	public Integer registerUser(User user, String password, Boolean isAdmin) throws OpenBookLibraryException {
		try {
		
			Optional<User> optionalEmployee = userRepo.findByMailId(user.getUserEmail());
			if(optionalEmployee.isPresent()) {
				throw new OpenBookLibraryException("Account with mail id already exists");
			}
			Optional<User> _optionalEmployee = userRepo.findByPhoneNo(user.getUserPhoneNo());
			if(_optionalEmployee.isPresent()) {
				throw new OpenBookLibraryException("Account with phone No already exists");
			}
			user.setIsAdmin(isAdmin);
			userRepo.save(user);
			User _user = userRepo.findByMailId(user.getUserEmail()).get();
			Credential credential = new Credential();
			credential.setUserId(_user.getUserId());
			credential.setMailId(_user.getUserEmail());

			byte[] salt = HashAlgorithmService.createSalt();
			String hashedPassword = HashAlgorithmService.hashedPassword(password, salt);
			credential.setHashedPassword(hashedPassword);
			credential.setSaltArray(salt);

			credentialRepo.save(credential);

			return user.getUserId();
		} catch (Exception exception) {

			throw new OpenBookLibraryException(exception.getMessage());
		}
	}

	public List<Book> viewBooks() {
	
		return bookRepo.findAll();
		
	}

	public IssueBook issueBook(Date issueBookDate, Date returnDate, Integer bookId, Integer issuedToUserId) throws OpenBookLibraryException {
		
		//check if book is already booked, availableCopies are zero and throw exception if booked
		
		User issuedToUser = Utility.utilityObject.getUserFromUserId(issuedToUserId, userRepo);
		Book issuedBook = Utility.utilityObject.getBookFromBookId(bookId, bookRepo);
		System.out.println(issuedToUser);
		IssueBook issueBook = new IssueBook();
		issueBook.setIssueDate(issueBookDate);
		issueBook.setIssuedToUser(issuedToUser);
		issueBook.setIssuedBook(issuedBook);
		issueBook.setReturnDate(returnDate);
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

	
}
