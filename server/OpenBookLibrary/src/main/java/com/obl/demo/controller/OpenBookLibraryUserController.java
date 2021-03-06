package com.obl.demo.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.obl.demo.bean.Book;
import com.obl.demo.bean.BookAuthor;
import com.obl.demo.bean.BookCategory;
import com.obl.demo.bean.BookPublisher;
import com.obl.demo.bean.IssueBook;
import com.obl.demo.bean.User;
import com.obl.demo.exception.OpenBookLibraryException;
import com.obl.demo.service.OpenBookLibraryUserService;

@CrossOrigin("http://localhost:4200")
@RestController
public class OpenBookLibraryUserController {

	@Autowired
	OpenBookLibraryUserService userService;
	
	@PostMapping("/register/user/{password}/{isAdmin}")
	public Integer registerUser(@RequestBody User user, @PathVariable("password") String password, @PathVariable("isAdmin") Boolean isAdmin) throws OpenBookLibraryException   {
		return userService.registerUser(user, password,isAdmin);
	}
	
	@PostMapping("/login/{email}/{password}")
	public User loginUser(@PathVariable String email, @PathVariable String password)throws OpenBookLibraryException {
		return userService.areCredentialsMatched(email, password);
	}
	
	@PutMapping("/change/password/{phoneNo}/{newPassword}")
	public User changePassword(@PathVariable String phoneNo, @PathVariable String newPassword) throws OpenBookLibraryException{
		return userService.updatePassword(phoneNo, newPassword);
	}
	
	@GetMapping("/view/allBooks")
	public List<Book> viewBooks() {
		return userService.viewBooks();
	}
	
	@PostMapping("/issueBook/{issuedDate}/{returnDate}/{bookId}/{issuedToUserId}") 
	public IssueBook issueBook(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date issuedDate,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date returnDate,@PathVariable Integer bookId, @PathVariable Integer issuedToUserId) throws OpenBookLibraryException {
		return userService.issueBook(new Date(issuedDate.getTime()),new Date(returnDate.getTime()), bookId, issuedToUserId);
	}

	
	
	
}
