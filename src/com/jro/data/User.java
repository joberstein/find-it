package com.jro.data;

public class User {
	private long id;
	private String email;
	private String username;
	private String password;

	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (!(o instanceof User)) {
			return false;
		}
		User temp = (User) o;
		return this.id == temp.id &&
				this.username.equals(temp.username) &&
				this.password.equals(temp.password) &&
				this.email.equals(temp.email);
	}
	
	@Override
	public int hashCode() {
		return new Long(this.id).intValue();
	}
}
