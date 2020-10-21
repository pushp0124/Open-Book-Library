package com.obl.mailservice.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.obl.mailservice.models.Book;
import com.obl.mailservice.models.User;
import com.obl.mailservice.models.BookTransaction;
import com.obl.mailservice.models.MailResponse;
import com.obl.mailservice.services.EmailService;


@RestController
@CrossOrigin("http://localhost:4200")
public class LibraryEmailController {

	
	@Autowired
	private EmailService mailService;

	@PostMapping("/notify/book-delay")
	public void notifyBookDelay(@RequestBody List<BookTransaction> bookTransactions) {
		mailService.notifyBookDelay(bookTransactions);

	}
	
	
	@PostMapping("/notify/book-borrowed")
	public MailResponse notifyBookIssued(@RequestBody BookTransaction bookTransaction) {
		return mailService.notifyBookBorrowed(bookTransaction);

	}
	
	@PostMapping("/notify/book-deposited")
	public MailResponse notifyBookDeposited(@RequestBody BookTransaction bookTransaction) {
		
		return mailService.notifyBookDeposited(bookTransaction);

	}
	
	
	@PostMapping("/notify/book-available")
	public void notifyBookAvailable(@RequestBody Book book) {
		
		mailService.notifyBookAvailable(book);

	}
	
	
	@PostMapping("/send/verificationCode")
	public void sendVerificationCode(@RequestBody User user) {
		
		mailService.sendVerificationCode(user);

	}
	
	
	@PostMapping("/send/welcomeMail")
	public void sendWelcomeMail(@RequestBody User user) {
		
		mailService.sendWelcomeMail(user);

	}
	
	@PostMapping("/user/forgotPassword/{mailId}")
	public void userForgotPassword(@PathVariable String mailId, @RequestBody String unique_password) {
		
		mailService.userForgotPassword(mailId,unique_password);

	}
	
	
	@PostMapping("/user/changePassword")
	public void userChangePassword(@RequestBody User user) {
		
		mailService.userChangePassword(user);

	}
	
}
