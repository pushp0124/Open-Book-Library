package com.obl.gateway_security.services;

import java.util.List;

import com.obl.gateway_security.exceptions.OpenBookLibraryException;
import com.obl.gateway_security.models.AuthResponse;
import com.obl.gateway_security.models.LibraryConstants;
import com.obl.gateway_security.models.User;

public interface AuthenticationService {
	AuthResponse login(String username, String password);
	
	User registerUser(User user);

	Boolean isValidToken(String token);

	String createNewToken(String token);
	
	List<User> viewAllPatrons();
	
	User getUser(Integer userId) throws OpenBookLibraryException;
	
	Boolean disableUser(Integer userId) throws OpenBookLibraryException;
	
	Boolean verifyCode(String code);
	
	LibraryConstants getLibraryConstants() throws OpenBookLibraryException;
	
	Boolean updateLibraryConstants(LibraryConstants constants) throws OpenBookLibraryException;
	
	Boolean forgotPassword(String mailId) throws OpenBookLibraryException;
	
	Boolean changePassword(String userName,String newPassword,String oldPassword) throws OpenBookLibraryException;
	
	Boolean updateUserProfile(User user, String userName, String password) throws OpenBookLibraryException;
	
}
