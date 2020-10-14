package com.obl.gateway_security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.obl.gateway_security.exceptions.OpenBookLibraryException;
import com.obl.gateway_security.models.LibraryUserDetails;
import com.obl.gateway_security.models.Role;
import com.obl.gateway_security.models.User;
import com.obl.gateway_security.repos.UserRepo;

@Service
public class LibraryUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
    	Optional<User> optionalUser = userRepo.findByMailId(email); 
    	System.out.println(optionalUser);
        if (optionalUser.isEmpty() || optionalUser.get().getRoles() == null || optionalUser.get().getRoles().isEmpty()) {
            throw new OpenBookLibraryException("No account registered with the provided mail address", HttpStatus.UNAUTHORIZED);
        }
        
        User user = optionalUser.get();
        String [] authorities = new String[user.getRoles().size()];
        int count=0;
        for (Role role : user.getRoles()) {
            //NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
            //Since we are using custom token using JWT we should add ROLE_ prefix
            authorities[count] = "ROLE_"+role.getName();
            count++;
        }
        LibraryUserDetails userDetails  = new LibraryUserDetails(user.getUserEmail(),user.getPassword(),user.getActive(),
                user.getIsLocked(), user.getIsExpired(),user.getIsEnabled(),authorities);
        return userDetails;
    }
}
