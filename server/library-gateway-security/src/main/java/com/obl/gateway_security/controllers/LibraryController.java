package com.obl.gateway_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.obl.gateway_security.exceptions.OpenBookLibraryException;
import com.obl.gateway_security.models.AuthRequest;
import com.obl.gateway_security.models.AuthResponse;
import com.obl.gateway_security.models.ChangePasswordRequest;
import com.obl.gateway_security.models.LibraryConstants;
import com.obl.gateway_security.models.UpdateUserProfile;
import com.obl.gateway_security.models.User;
import com.obl.gateway_security.services.AuthenticationService;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:4200")
public class LibraryController {


    @Autowired
    private AuthenticationService authService;


    @PostMapping("/signin")
    public AuthResponse login(@RequestBody AuthRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest.getUsername(),loginRequest.getPassword());

         return authResponse;
    }

    @PostMapping("/valid/token")
    public Boolean isValidToken (@RequestHeader(value="Authorization") String token) {
        return true;
    }



    @PostMapping("/register")
    public User registerUser (@RequestBody User user) {
        return authService.registerUser(user);
    }
    
    
    @GetMapping("/view/all/patrons")
	public List<User> viewAllPatrons() {
    	return authService.viewAllPatrons();
    }
	
    @GetMapping("/get/user/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
    	return authService.getUser(userId);
    }
    
    @PutMapping("/disableUser/{userId}")
    public Boolean disableUser(@PathVariable Integer userId) {
    	return authService.disableUser(userId);
    }
    
    
    @GetMapping("/verify")
    public String verifyCode(@RequestParam("code") String code) {
    	Boolean isCodeVerified = authService.verifyCode(code);
    	if(isCodeVerified) {
    		return "Mail is verified successfully, please login to continue";
    	} else {
    		return "Code is invalid";
    	}
    }
    
    @GetMapping("/forgotPassword/{userName}")
    public Boolean forgotPassword(@PathVariable String userName) {
    	return authService.forgotPassword(userName);
    }
    
    @PutMapping("/changePassword")
    public Boolean changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws OpenBookLibraryException {
    	System.out.println("Change" + changePasswordRequest);
    	return authService.changePassword(changePasswordRequest.getUserName(), changePasswordRequest.getNewPassword(), changePasswordRequest.getOldPassword());
    }
    @GetMapping("/get/constants")
    public LibraryConstants getLibraryConstants() throws OpenBookLibraryException {
    	return authService.getLibraryConstants();
    }
    
    @PutMapping("/update/constants")
    public Boolean updateLibraryConstants(@RequestBody LibraryConstants constants) throws OpenBookLibraryException {
    	return authService.updateLibraryConstants(constants);
    }
    
    @PutMapping("/update/userProfile")
    public Boolean updateUserProfile(@RequestBody UpdateUserProfile updatedUserProfile) throws OpenBookLibraryException {
    	return authService.updateUserProfile(updatedUserProfile.getUpdatedUser(), updatedUserProfile.getAuthRequest().getUsername(), updatedUserProfile.getAuthRequest().getPassword());
    }
	
}
