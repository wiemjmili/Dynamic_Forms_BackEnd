package com.app.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {
	@Id
	private String id;
	private String username;
	private String email;
	private String password;
	@DBRef
	private List<Group> groups;
	@DBRef
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public User(String username, String email, String password, Set<Role> roles, List<Group> groups) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.groups = groups;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
