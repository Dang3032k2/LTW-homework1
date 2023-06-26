package com.thuvien.btl.User;

public class User {
	private int iduser;
	private String name;
	private String email;
	private String password;
	private String newpw;
	private boolean admin;
	public User() {
	}
	public User(int iduser, String name, String email, String password, boolean admin) {
		super();
		this.iduser = iduser;
		this.name = name;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
	
	public User(int iduser, String password, String newpw) {
		super();
		this.iduser = iduser;
		this.password = password;
		this.newpw = newpw;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNewpw() {
		return newpw;
	}
	public void setNewpw(String newpw) {
		this.newpw = newpw;
	}
	
	
}
