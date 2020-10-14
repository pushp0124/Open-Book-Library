package com.obl.gateway_security.models;

import java.util.Set;

public class AuthResponse {

	private String accessToken ;

	private Integer loggedInUserId;
	
	private Set<Role> loggedInUserRoles;
	
	private String loggedInUserEmail;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	public Set<Role> getLoggedInUserRoles() {
		return loggedInUserRoles;
	}

	public void setLoggedInUserRoles(Set<Role> loggedInUserRoles) {
		this.loggedInUserRoles = loggedInUserRoles;
	}

	public String getLoggedInUserEmail() {
		return loggedInUserEmail;
	}

	public void setLoggedInUserEmail(String loggedInUserEmail) {
		this.loggedInUserEmail = loggedInUserEmail;
	}

	@Override
	public String toString() {
		return "AuthResponse [accessToken=" + accessToken + ", loggedInUserId=" + loggedInUserId
				+ ", loggedInUserRoles=" + loggedInUserRoles + ", loggedInUserEmail=" + loggedInUserEmail + "]";
	}


	

}
