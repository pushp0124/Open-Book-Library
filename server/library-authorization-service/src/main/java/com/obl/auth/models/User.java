package com.obl.auth.models;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(unique = true, nullable = false)
	private String userName;
	
	@Column(unique = true, nullable = false)
	private String userEmail;
	
	@Column(unique = true, nullable = false)
	private String userPhoneNo;
	
	@Column(unique = true, nullable = false)
	private String userAddress;
	
	@Column(nullable = false)
	private Boolean isAdmin;
	
	@Column(nullable = false)
	private String hashedPassword; 
	
	@Column(nullable = false)
	private byte[] saltArray;
	
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
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public byte[] getSaltArray() {
		return saltArray;
	}
	public void setSaltArray(byte[] saltArray) {
		this.saltArray = saltArray;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPhoneNo="
				+ userPhoneNo + ", userAddress=" + userAddress + ", isAdmin=" + isAdmin + ", hashedPassword="
				+ hashedPassword + ", saltArray=" + Arrays.toString(saltArray) + "]";
	}
	
	
	
	
}

