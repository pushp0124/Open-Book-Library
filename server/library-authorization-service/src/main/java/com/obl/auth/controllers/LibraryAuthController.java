package com.obl.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obl.auth.exceptions.OpenBookLibraryException;
import com.obl.auth.models.User;
import com.obl.auth.services.AuthService;
//import com.obl.auth.services.LibraryUserDetailsService;
import com.obl.auth.utils.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class LibraryAuthController {

	@Autowired
	AuthService authService;
	
//	@Autowired
//	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

//	@Autowired
//	private LibraryUserDetailsService userDetailsService;
	
	@PostMapping("/register/user/{password}")
	public Integer registerUser(@RequestBody User user, @PathVariable("password") String password) throws OpenBookLibraryException{
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
	
//	@PostMapping("/login/{email}/{password}")
//	public ResponseEntity<?> createAuthenticationToken(@PathVariable String email, @PathVariable String password) throws OpenBookLibraryException {
//		try {
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(email, password)
//			);
//		}
//		catch (BadCredentialsException e) {
//			throw new OpenBookLibraryException("Incorrect username or password");
//		}
//
//
//		final UserDetails userDetails = userDetailsService
//				.loadUserByUsername(email);
//
//		final String jwt = jwtTokenUtil.generateToken(userDetails);
//
//		return ResponseEntity.ok(jwt);
//	}
	
	
}
