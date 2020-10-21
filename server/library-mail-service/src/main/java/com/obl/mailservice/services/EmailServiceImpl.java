package com.obl.mailservice.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.obl.mailservice.models.Book;
import com.obl.mailservice.models.BookTransaction;
import com.obl.mailservice.models.MailResponse;
import com.obl.mailservice.models.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private Configuration config;
	
	@Override
	@Async
	public void notifyBookDelay(List<BookTransaction> bookTransactions) {
	
		System.out.println(bookTransactions);
		for (BookTransaction bookTransaction : bookTransactions) {
			
			MailResponse response = new MailResponse();
			
			MimeMessage message = sender.createMimeMessage();
			
			Map<String, Object> delayModel = new HashMap<>();
			delayModel.put("bookTransaction", bookTransaction);
			String link = env.getProperty("frontend.url") + "/userhomepage/book/" + bookTransaction.getBorrowedBook().getBookId();
			delayModel.put("link", link);	
			
			try {
				// set mediaType
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
						StandardCharsets.UTF_8.name());
				// add attachment
				Template t = config.getTemplate("book-delayed.ftl");
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, delayModel);

				helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
				helper.setTo(bookTransaction.getBorrowedToUser().getUserEmail());
				helper.setText(html, true);
				helper.setSubject("Reminder Book Deposit Delay");
			
				sender.send(message);

				response.setMessage("Mail Sent Successfully to : " + bookTransaction.getBorrowedToUser().getUserEmail());
				response.setStatus(Boolean.TRUE);
				

			} catch (MessagingException | IOException | TemplateException e) {
				response.setMessage("Mail Sending failure : "+e.getMessage());
				response.setStatus(Boolean.FALSE);
				
			} 
			
			
		}
		
	}

	@Override
	@Async
	public MailResponse notifyBookBorrowed(BookTransaction bookTransaction) {
		

		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		
		Map<String, Object> borrowModel = new HashMap<>();
		borrowModel.put("bookTransaction", bookTransaction);
		String link = env.getProperty("frontend.url") + "/userhomepage/book/" + bookTransaction.getBorrowedBook().getBookId();
		borrowModel.put("link", link);
		
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment

			Template t = config.getTemplate("book-borrowed.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, borrowModel);

			helper.setTo(bookTransaction.getBorrowedToUser().getUserEmail());
			helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
			helper.setText(html, true);
			helper.setSubject("Book Borrowed Successfully");
			sender.send(message);

			response.setMessage("Mail Sent Successfully to : " + bookTransaction.getBorrowedToUser().getUserEmail());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		
		return response;
		
	}

	@Override
	@Async
	public MailResponse notifyBookDeposited(BookTransaction bookTransaction) {
		
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		
		Map<String, Object> depositModel = new HashMap<>();
		depositModel.put("bookTransaction", bookTransaction);
		
		String link = env.getProperty("frontend.url") + "/userhomepage/book/" + bookTransaction.getBorrowedBook().getBookId();
		depositModel.put("link", link);
		
		
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment

			Template t = config.getTemplate("book-deposited.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, depositModel);

			helper.setTo(bookTransaction.getBorrowedToUser().getUserEmail());
			helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
			helper.setText(html, true);
			helper.setSubject("Book Deposited Successfully");
			sender.send(message);

			response.setMessage("Mail Sent Successfully to : " + bookTransaction.getBorrowedToUser().getUserEmail());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}

	@Override
	@Async
	public void notifyBookAvailable(Book book) {
		
		MailResponse response = new MailResponse();
		
		List<User> usersToBeNotified = book.getToNotifyUsers();
		
		for (User user : usersToBeNotified) {
			
			
			MimeMessage message = sender.createMimeMessage();
			
			Map<String, Object> delayModel = new HashMap<>();
			delayModel.put("book", book);
			delayModel.put("userName", user.getUserName());
			
			String link = env.getProperty("frontend.url") + "/userhomepage/book/" + book.getBookId();
			delayModel.put("link", link);
			try {
				// set mediaType
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
						StandardCharsets.UTF_8.name());
				// add attachment

				Template t = config.getTemplate("book-available.ftl");
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, delayModel);

				helper.setTo(user.getUserEmail());
				helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
				helper.setText(html, true);
				helper.setSubject("Book Available For Borrow");
				sender.send(message);

				response.setMessage("Mail Sent Successfully to : " + user.getUserEmail());
				response.setStatus(Boolean.TRUE);
				

			} catch (MessagingException | IOException | TemplateException e) {
				response.setMessage("Mail Sending failure : "+e.getMessage());
				response.setStatus(Boolean.FALSE);
				
			}

		}
		
	
	}
	
	@Override
	@Async
	public void sendVerificationCode(com.obl.mailservice.models.User user) {

		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		
		Map<String, Object> codeModel = new HashMap<>();
		codeModel.put("verifyUser", user);
		String link = "http://localhost:8100/verify?code=" + user.getVerificationCode();
		codeModel.put("link", link);
		
		
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment

			Template t = config.getTemplate("verify-user.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, codeModel);

			helper.setTo(user.getUserEmail());
			helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
			helper.setText(html, true);
			helper.setSubject("Mail Verification - Open Book Library");
			sender.send(message);

			response.setMessage("Mail Sent Successfully to : " + user.getUserEmail());
			response.setStatus(Boolean.TRUE);
			
			System.out.println(response);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		
	}
	
	
	@Override
	@Async
	public void sendWelcomeMail(com.obl.mailservice.models.User user) {

		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		
		Map<String, Object> codeModel = new HashMap<>();
		codeModel.put("welcomeUser", user);
		String link = env.getProperty("frontend.url") + "/userlogin";
		codeModel.put("link", link);
		
		
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment

			Template t = config.getTemplate("welcome-mail.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, codeModel);

			helper.setTo(user.getUserEmail());
			helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
			helper.setText(html, true);
			helper.setSubject("Congratulations!");
			sender.send(message);

			response.setMessage("Mail Sent Successfully to : " + user.getUserEmail());
			response.setStatus(Boolean.TRUE);
			System.out.println(response);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
			System.out.println(response);
		}

		
	}

	@Override
	@Async
	public void userForgotPassword(String mailId, String unique_password) {
		

		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		
		Map<String, Object> passwordModel = new HashMap<>();
		
		String link = env.getProperty("frontend.url") + "/userlogin";
		passwordModel.put("password", unique_password);
		passwordModel.put("link", link);
		
		
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment

			Template t = config.getTemplate("forgot-password.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, passwordModel);

			helper.setTo(mailId);
			helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
			helper.setText(html, true);
			helper.setSubject("Password Reset");
			sender.send(message);

			response.setMessage("Mail Sent Successfully to : " + mailId);
			response.setStatus(Boolean.TRUE);
		}  catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
			System.out.println(response);
		}

		
		
	}

	@Override
	@Async
	public void userChangePassword(User user) {

		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		
		Map<String, Object> passwordModel = new HashMap<>();
		passwordModel.put("user", user);
		String link = env.getProperty("frontend.url") + "/userlogin";
		passwordModel.put("link", link);
		
		
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment

			Template t = config.getTemplate("change-password.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, passwordModel);

			helper.setTo(user.getUserEmail());
			helper.setFrom(new InternetAddress("open.book.library.pip@gmail.com", "Open Book Library Team"));
			helper.setText(html, true);
			helper.setSubject("Password Changed Successfully");
			sender.send(message);

			response.setMessage("Mail Sent Successfully to : " + user.getUserEmail());
			response.setStatus(Boolean.TRUE);
		}  catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
			System.out.println(response);
		}
		
	}
	

}