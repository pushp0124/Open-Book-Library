package com.obl.auth.services;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obl.auth.exceptions.OpenBookLibraryException;
import com.obl.auth.models.LoggedInUser;
import com.obl.auth.models.User;
import com.obl.auth.repos.UserRepo;
import com.obl.auth.utils.AuthUtility;


@Service
public class AuthServiceImpl implements AuthService {

	Map<String,LoggedInUser> authTokenMap = new HashMap<String,LoggedInUser>();

	@Autowired
	UserRepo userRepo;


	public User updatePassword(String phoneNo, String newPassword) throws OpenBookLibraryException {

		return null;
	}


	public User areCredentialsMatched(String mailId, String password) throws OpenBookLibraryException{

		try {


			Optional<User> optionalUser = userRepo.findByMailId(mailId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
//				String hashUserPassword = HashAlgorithmService.hashedPassword(password, user.getSaltArray());
				if (password.equals(user.getPassword())) {
					return userRepo.findByMailId(mailId).get();

				}
				throw new OpenBookLibraryException("Password mismatch");

			} else {
				throw new OpenBookLibraryException("Mail Id is not registered!");
			}

		} catch (Exception exception) {
			System.out.println(exception);
			throw new OpenBookLibraryException(exception.getMessage());

		}
	}

	public Integer registerUser(User user, String password) throws OpenBookLibraryException {
		try {

			Optional<User> optionalEmployee = userRepo.findByMailId(user.getUserEmail());
			if(optionalEmployee.isPresent()) {
				throw new OpenBookLibraryException("Account with mail id already exists");
			}
			Optional<User> _optionalEmployee = userRepo.findByPhoneNo(user.getUserPhoneNo());
			if(_optionalEmployee.isPresent()) {
				throw new OpenBookLibraryException("Account with phone No already exists");
			}

//			byte[] salt = HashAlgorithmService.createSalt();
//			String hashedPassword = HashAlgorithmService.hashedPassword(password, salt);
			user.setPassword(password);
//			user.setSaltArray(salt);

			user = userRepo.save(user);

			return user.getUserId();
		} catch (Exception exception) {

			throw new OpenBookLibraryException(exception.getMessage());
		}
	}

	public User getUserFromUserId(Integer userId) throws OpenBookLibraryException {
		return AuthUtility.utilityObject.getUserFromUserId(userId, userRepo);
	}

	public String generateAuthToken() {
		String key = UUID.randomUUID().toString();
		while(authTokenMap.containsKey(key)) {
			key = UUID.randomUUID().toString();
		}

		return key;

	}
	public String issueToken(String mailId) throws OpenBookLibraryException{
//		User user = AuthUtility.utilityObject.getUserFromMailId(mailId, userRepo);
//		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
//		LoggedInUser loggedInUser = new LoggedInUser(user.getUserEmail(), user.getUserName() , user.getIsAdmin(),currentTimeStamp);
//		String key = generateAuthToken();
//		authTokenMap.put(key, loggedInUser);
//		return key;
		
		return null;

	}

	public Boolean checkIfTokenValid(String token, String mailId) throws OpenBookLibraryException {
		for (Map.Entry<String,LoggedInUser> entry : authTokenMap.entrySet())  {
			LoggedInUser user = entry.getValue();
			if(user.getUserId().equals(mailId)) {
				return true;
			}
		} 
		return false;
	}


}
