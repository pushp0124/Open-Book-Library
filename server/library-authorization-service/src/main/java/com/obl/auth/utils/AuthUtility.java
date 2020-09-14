package com.obl.auth.utils;

import java.util.Optional;

import com.obl.auth.exceptions.OpenBookLibraryException;
import com.obl.auth.models.User;
import com.obl.auth.repos.UserRepo;

public class AuthUtility {

	public static final AuthUtility utilityObject = new AuthUtility();
	
	public static final String dateTimeFormatter = "yyyy-MM-dd";

	public User getUserFromUserId(Integer userId, UserRepo userRepo) throws OpenBookLibraryException {
		Optional<User> optionalUserObj = userRepo.findById(userId);
		if(optionalUserObj.isEmpty()) {
			throw new OpenBookLibraryException("Account with UserId " + userId + " doesn't exist for User");
		} 
		return optionalUserObj.get();
	}
	
	public User getUserFromMailId(String mailId, UserRepo userRepo) throws OpenBookLibraryException {
		Optional<User> optionalUserObj = userRepo.findByMailId(mailId);
		if(optionalUserObj.isEmpty()) {
			throw new OpenBookLibraryException("Account with mail Id " + mailId + " doesn't exist for User");
		} 
		return optionalUserObj.get();
	}

}
