package com.obl.auth.models;

import java.sql.Timestamp;

public class LoggedInUser {
	
	private String userId; //userEmail
	private String userName;
	private Boolean isAdmin;
	private Timestamp lastAccessedTime;
	
	
	public LoggedInUser(String userId, String userName, Boolean isAdmin, Timestamp lastAccessedTime) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.isAdmin = isAdmin;
		this.lastAccessedTime = lastAccessedTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Timestamp getLastAccessedTime() {
		return lastAccessedTime;
	}
	public void setLastAccessedTime(Timestamp lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}
	@Override
	public String toString() {
		return "LoggedInUser [userId=" + userId + ", userName=" + userName + ", isAdmin=" + isAdmin
				+ ", lastAccessedTime=" + lastAccessedTime + "]";
	}
	
	

}
