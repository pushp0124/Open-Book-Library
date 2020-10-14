package com.obl.mailservice.models;

import java.util.Set;

public class User {

	
	private Integer userId;
	
	
	private String userName;
	
	private String userEmail;
	
	private String userPhoneNo;
	
	private String userAddress;
	
	private String password; 
	
	private Integer active=1;
	
	private Boolean isLocked = true; // initially true, as for verify email
	
	private Boolean isExpired = false;
	
	private Boolean isEnabled = true; // block user for heavy book lossages
	
	private String verificationCode;
    
    private Set<Role> roles;
    
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhoneNo() {
		return userPhoneNo;
	}
	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Boolean getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPhoneNo="
				+ userPhoneNo + ", userAddress=" + userAddress + ", password=" + password + ", active=" + active
				+ ", isLocked=" + isLocked + ", isExpired=" + isExpired + ", isEnabled=" + isEnabled
				+ ", verificationCode=" + verificationCode + ", roles=" + roles + "]";
	}
	
	
   
	
}