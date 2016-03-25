package org.twt.entity;

public class User {
	private Integer id;
	private String username;
	private String source;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public User(Integer id, String username, String source) {
		super();
		this.id = id;
		this.username = username;
		this.source = source;
	}

	public User() {
		super();
	}

	public String toString() {
		return "User [id=" + id + ", username=" + username + ", source=" + source + "]";
	}

}
