package com.test.springboot.dao.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private Date regtime;
	private String username;
	private String password;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public Date getRegtime() {
		return regtime;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}