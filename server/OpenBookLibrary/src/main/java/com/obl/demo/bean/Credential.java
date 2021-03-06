package com.obl.demo.bean;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Credential {


	@Id
	private Integer userId;
	
	@Column(unique=true, nullable = false)
	private String mailId;
	
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

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
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
		return "Credential [userId=" + userId + ", mailId=" + mailId + ", hashedPassword=" + hashedPassword
				+ ", saltArray=" + Arrays.toString(saltArray) + "]";
	}
	
	
	
}
