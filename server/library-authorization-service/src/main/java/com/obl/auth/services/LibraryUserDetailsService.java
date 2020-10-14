package com.obl.auth.services;

import java.util.ArrayList;
import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.obl.auth.models.User;
import com.obl.auth.repos.UserRepo;
//
//@Service
//public class LibraryUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    UserRepo userRepo;
//	
//    public UserDetails loadUserByUsername(String mailId) throws UsernameNotFoundException {
//         Optional<User> optionalUser =  userRepo.findByMailId(mailId);
//         if(optionalUser.isEmpty()) {
//        	 throw new UsernameNotFoundException("User with mail Id " + mailId + " not found");
//         }
//         User user = optionalUser.get();
//    	org.springframework.security.core.userdetails.User authUser = new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(), new ArrayList<>());
//    	return authUser;
//    	
//    }
//}