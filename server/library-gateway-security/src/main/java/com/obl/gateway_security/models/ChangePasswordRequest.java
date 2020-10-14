package com.obl.gateway_security.models;

public class ChangePasswordRequest {
	
	private String userName;
	private String oldPassword;
	private String newPassword;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "ChangePasswordRequest [userName=" + userName + ", oldPassword=" + oldPassword + ", newPassword="
				+ newPassword + "]";
	}
	

}
