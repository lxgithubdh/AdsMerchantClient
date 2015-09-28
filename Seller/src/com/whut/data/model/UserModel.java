package com.whut.data.model;

/**
 * 存储用户信息
 * @author lx
 */
public class UserModel {

	
	private String userName;             //用户名
	private String password;              //密码
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
