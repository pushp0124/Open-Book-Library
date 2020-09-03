package com.obl.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obl.auth.beans.User;
import com.obl.auth.exceptions.OpenBookLibraryException;
import com.obl.auth.services.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class LibraryAuthController {

	@Autowired
	AuthService authService;
	
	@PostMapping("/register/user/{password}")
	public Integer registerUser(@RequestBody User user, @PathVariable("password") String password) throws OpenBookLibraryException{
		System.out.println("Helo");
		return authService.registerUser(user, password);
	}
	
	@PostMapping("/login/{email}/{password}")
	public User loginUser(@PathVariable String email, @PathVariable String password) throws OpenBookLibraryException {
		return authService.areCredentialsMatched(email, password);
	}
	
	@PutMapping("/change/password/{phoneNo}/{newPassword}")
	public User changePassword(@PathVariable String phoneNo, @PathVariable String newPassword) throws OpenBookLibraryException {
		return authService.updatePassword(phoneNo, newPassword);
	}
	
	@GetMapping("/user/{userId}")
	public User getUserFromUserId(@PathVariable("userId") Integer userId) throws OpenBookLibraryException {
		
		return authService.getUserFromUserId(userId);
	}
	
}
