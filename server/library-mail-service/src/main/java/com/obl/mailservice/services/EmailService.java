package com.obl.mailservice.services;

import java.util.List;

import com.obl.mailservice.exceptions.OpenBookLibraryException;
import com.obl.mailservice.models.Book;
import com.obl.mailservice.models.BookTransaction;
import com.obl.mailservice.models.MailResponse;
import com.obl.mailservice.models.User;

public interface EmailService {

	public void notifyBookDelay(List<BookTransaction> bookTransactions);
	
	public MailResponse notifyBookBorrowed(BookTransaction bookTransaction);
	
	public MailResponse notifyBookDeposited(BookTransaction bookTransaction);
	
	public void notifyBookAvailable(Book book);
	
	public void sendVerificationCode(User user);
	
	public void sendWelcomeMail(User user);
	
	public void userForgotPassword(String mailId, String unique_password);
	
	public void userChangePassword(User user);

}