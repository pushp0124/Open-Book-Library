package com.obl.gateway_security.utils;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.obl.gateway_security.exceptions.OpenBookLibraryException;
import com.obl.gateway_security.models.User;
import com.obl.gateway_security.repos.UserRepo;

public class AuthUtility {

	public static final AuthUtility utilityObject = new AuthUtility();
	
	public static final String dateTimeFormatter = "yyyy-MM-dd";

	public User getUserFromUserId(Integer userId, UserRepo userRepo) throws OpenBookLibraryException {
		Optional<User> optionalUserObj = userRepo.findById(userId);
		if(optionalUserObj.isEmpty()) {
			throw new OpenBookLibraryException("Account with UserId " + userId + " doesn't exist for User", HttpStatus.NOT_FOUND);
		} 
		return optionalUserObj.get();
	}
	
	public User getUserFromMailId(String mailId, UserRepo userRepo) throws OpenBookLibraryException {
		Optional<User> optionalUserObj = userRepo.findByMailId(mailId);
		if(optionalUserObj.isEmpty()) {
			throw new OpenBookLibraryException("Account with mail Id " + mailId + " doesn't exist for User", HttpStatus.NOT_FOUND);
		} 
		return optionalUserObj.get();
	}

}
