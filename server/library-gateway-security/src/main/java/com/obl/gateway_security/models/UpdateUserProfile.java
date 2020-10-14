package com.obl.gateway_security.models;

public class UpdateUserProfile {
	 private User updatedUser; 
	 private AuthRequest authRequest;
	public User getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}
	public AuthRequest getAuthRequest() {
		return authRequest;
	}
	public void setAuthRequest(AuthRequest authRequest) {
		this.authRequest = authRequest;
	}
	 
	 

}
