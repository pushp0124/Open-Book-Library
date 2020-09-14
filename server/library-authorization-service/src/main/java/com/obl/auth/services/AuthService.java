package com.obl.auth.services;

import com.obl.auth.exceptions.OpenBookLibraryException;
import com.obl.auth.models.User;

public interface AuthService {

	public User updatePassword(String phoneNo, String newPassword) throws OpenBookLibraryException;
	public User areCredentialsMatched(String mailId,String password) throws OpenBookLibraryException ; 

	public Integer registerUser(User user, String password) throws OpenBookLibraryException;

	public User getUserFromUserId(Integer userId) throws OpenBookLibraryException;

	public String issueToken(String mailId) throws OpenBookLibraryException;

	public Boolean checkIfTokenValid(String token, String mailId) throws OpenBookLibraryException;



}
