package com.obl.gateway_security.services;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.obl.gateway_security.exceptions.OpenBookLibraryException;
import com.obl.gateway_security.models.AuthResponse;
import com.obl.gateway_security.models.LibraryConstants;
import com.obl.gateway_security.models.Role;
import com.obl.gateway_security.models.User;
import com.obl.gateway_security.repos.LibraryConstantsRepo;
import com.obl.gateway_security.repos.RoleRepo;
import com.obl.gateway_security.repos.UserRepo;
import com.obl.gateway_security.security.JwtTokenProvider;
import com.obl.gateway_security.utils.AuthUtility;

@Service 
public class AuthenticationServiceImpl implements AuthenticationService {


	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepo userRepository;
	@Autowired
	private RoleRepo roleRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired 
	private LibraryConstantsRepo constantsRepo;

	@Override
	public AuthResponse login(String username, String password) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
				password));
		Optional<User> optionalUser = userRepository.findByMailId(username);
		System.out.println(optionalUser.get());
		if (optionalUser.isEmpty() || optionalUser.get().getRoles() == null || optionalUser.get().getRoles().isEmpty()) {
			throw new OpenBookLibraryException("No account registered with the provided mail address", HttpStatus.UNAUTHORIZED);
		}

		User user = optionalUser.get();
		System.out.println("Service" + user);
		//NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
		//Since we are using custom token using JWT we should add ROLE_ prefix
		String token =  jwtTokenProvider.createToken(username, user.getRoles().stream()
				.map((Role role)-> "ROLE_"+role.getName()).filter(Objects::nonNull).collect(Collectors.toList()));

		AuthResponse authResponse = new AuthResponse();
		authResponse.setAccessToken(token);
		authResponse.setLoggedInUserId(user.getUserId());
		authResponse.setLoggedInUserRoles(user.getRoles());
		authResponse.setLoggedInUserEmail(user.getUserEmail());
		return authResponse;
	}


	@Override
	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> roles = user.getRoles();
		Set<Role> _roles = new HashSet<Role>();
		for (Role role : roles) {
			Optional<Role> optionalRole = roleRepository.getRoleFromRoleName(role.getName());
			if(optionalRole.isPresent()) {
				_roles.add(optionalRole.get());
			} else {
				role = roleRepository.save(role);
				_roles.add(role);
			}
		}
		user.setRoles(_roles);
		String randomCode = UUID.randomUUID().toString();
		user.setVerificationCode(randomCode);
		userRepository.save(user);
		restTemplate.postForObject("http://library-mail-service/send/verificationCode", user, Object.class);


		return user;
	}



	@Override
	public Boolean isValidToken(String token) {
		return jwtTokenProvider.validateToken(token);
	}

	@Override
	public String createNewToken(String token) {
		String username = jwtTokenProvider.getUsername(token);
		List<String>roleList = jwtTokenProvider.getRoleList(token);
		String newToken =  jwtTokenProvider.createToken(username,roleList);
		return newToken;
	}


	@Override
	public List<User> viewAllPatrons() {
		List<User> users = new ArrayList<User>();
		List<User> _users = userRepository.findAll();
		for (User user : _users) {
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				if(role.getName().equals("ROLE_USER")) {
					users.add(user);
				}
			}
		}
		return users;
	}

	@Override
	public User getUser(Integer userId) throws OpenBookLibraryException {
		return AuthUtility.utilityObject.getUserFromUserId(userId, userRepository);
	}

	@Override
	public Boolean disableUser(Integer userId) throws OpenBookLibraryException {
		User user = AuthUtility.utilityObject.getUserFromUserId(userId, userRepository);
		user.setIsEnabled(false);
		userRepository.save(user);
		return true;
	}

	@Override
	public Boolean verifyCode(String code) {
		Optional<User> optionalUser = userRepository.getUserByVerificationCode(code);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setIsLocked(false);
			userRepository.save(user);
			restTemplate.postForObject("http://library-mail-service/send/welcomeMail", user, Object.class);
			return true;
		}
		return false;

	}


	@Override
	public LibraryConstants getLibraryConstants() throws OpenBookLibraryException {
		List<LibraryConstants> constantsList = constantsRepo.findAll();
		if(constantsList.size() == 0) {
			throw new OpenBookLibraryException("Constants are not initialized", HttpStatus.FAILED_DEPENDENCY);
		} else if(constantsList.size() > 1) {
			throw new OpenBookLibraryException("More than one row in constants table, which to choose ?", HttpStatus.FAILED_DEPENDENCY);
		}

		return constantsList.get(constantsList.size() - 1);
	}


	@Override
	public Boolean updateLibraryConstants(LibraryConstants constants) throws OpenBookLibraryException {
		List<LibraryConstants> constantsList = constantsRepo.findAll();
		if(constantsList.size() == 0) {
			throw new OpenBookLibraryException("Constants are not initialized", HttpStatus.FAILED_DEPENDENCY);
		} else if(constantsList.size() > 1) {
			throw new OpenBookLibraryException("More than one row in constants table, which to choose ?", HttpStatus.FAILED_DEPENDENCY);
		}
		LibraryConstants _constants = constantsList.get(constantsList.size() - 1);
		_constants.setBookBorrowDays(constants.getBookBorrowDays());
		_constants.setLateDepositFinePerDay(constants.getLateDepositFinePerDay());
		_constants.setMaximumBooksLimit(constants.getMaximumBooksLimit());


		constantsRepo.save(_constants);
		return true;
	}

	@Override
	public Boolean forgotPassword(String userName) throws OpenBookLibraryException {
		restTemplate.postForObject("http://library-mail-service/user/forgotPassword", userName, Boolean.class);
		return true;
	}


	@Override
	public Boolean changePassword(String userName, String newPassword, String oldPassword) throws OpenBookLibraryException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,
				oldPassword));
		Optional<User> optionalUser = userRepository.findByMailId(userName);
		System.out.println(optionalUser.get());
		if (optionalUser.isEmpty() || optionalUser.get().getRoles() == null || optionalUser.get().getRoles().isEmpty()) {
			throw new OpenBookLibraryException("No account registered with the provided mail address", HttpStatus.UNAUTHORIZED);
		}

		User user = optionalUser.get();

		user.setPassword(passwordEncoder.encode(newPassword));
		user = userRepository.save(user);
		restTemplate.postForObject("http://library-mail-service/user/changePassword", user, Boolean.class);
		return true;
	}


	@Override
	public Boolean updateUserProfile(User updatedUser, String userName, String password) throws OpenBookLibraryException {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,
				password));
		Optional<User> optionalUser = userRepository.findByMailId(userName);
		if (optionalUser.isEmpty() || optionalUser.get().getRoles() == null || optionalUser.get().getRoles().isEmpty()) {
			throw new OpenBookLibraryException("No account registered with the provided mail address", HttpStatus.UNAUTHORIZED);
		}
		User user = optionalUser.get();
		user.setUserName(updatedUser.getUserName());
		user.setUserAddress(user.getUserAddress());
		user.setUserPhoneNo(user.getUserPhoneNo());
		
		
		
		if(!updatedUser.getUserEmail().equals(user.getUserEmail())) {
			if(userRepository.findByMailId(updatedUser.getUserEmail()).isPresent()) {
				throw new OpenBookLibraryException("This mail id already registered !", HttpStatus.FORBIDDEN);
			}
			user.setIsLocked(true);
			user.setUserEmail(updatedUser.getUserEmail());
			restTemplate.postForObject("http://library-mail-service/send/verificationCode", updatedUser, Object.class);

			
		}
		
		user = userRepository.save(user);
		

		return true;

	}


}
