package com.obl.gateway_security.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class LibraryUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private Integer active;
	private boolean isLocked;
	private boolean isExpired;
	private boolean isEnabled;
	private List<GrantedAuthority> grantedAuthorities;


	public LibraryUserDetails() {
		super();
	}
	public LibraryUserDetails(String username, String password,Integer active, boolean isLocked, boolean isExpired, boolean isEnabled, String [] authorities) {
		this.username = username;
		this.password = password;
		this.active = active;
		this.isLocked = isLocked;
		this.isExpired = isExpired;
		this.isEnabled = isEnabled;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
	}


	public LibraryUserDetails(String username,  String [] authorities) {
		this.username = username;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return active == 1;
	}

	public boolean isAccountNonLocked() {
		return !isLocked;
	}

	public boolean isCredentialsNonExpired() {
		return !isExpired;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

}
