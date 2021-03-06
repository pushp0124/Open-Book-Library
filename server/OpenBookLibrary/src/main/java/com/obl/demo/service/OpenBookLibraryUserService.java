package com.obl.demo.service;

import java.sql.Date;
import java.util.List;

import com.obl.demo.bean.Book;
import com.obl.demo.bean.IssueBook;
import com.obl.demo.bean.User;
import com.obl.demo.exception.OpenBookLibraryException;


public interface OpenBookLibraryUserService {

	public User updatePassword(String phoneNo, String newPassword) throws OpenBookLibraryException;

	public User areCredentialsMatched(String mailId,String password) throws OpenBookLibraryException ; 

	public Integer registerUser(User user, String password,Boolean isAdmin) throws OpenBookLibraryException;

	public List<Book> viewBooks();

	public IssueBook issueBook(Date issueBookDate,Date returnDate, Integer bookId, Integer issuedToUserId) throws OpenBookLibraryException;

	

}
