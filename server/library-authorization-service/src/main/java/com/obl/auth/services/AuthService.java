package com.obl.auth.services;

import com.obl.auth.beans.User;
import com.obl.auth.exceptions.OpenBookLibraryException;

public interface AuthService {

	public User updatePassword(String phoneNo, String newPassword) throws OpenBookLibraryException;

	public User areCredentialsMatched(String mailId,String password) throws OpenBookLibraryException ; 

	public Integer registerUser(User user, String password) throws OpenBookLibraryException;
	
	public User getUserFromUserId(Integer userId) throws OpenBookLibraryException;

}
