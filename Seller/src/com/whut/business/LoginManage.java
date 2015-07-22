package com.whut.business;

import android.os.Handler;


/**
 * 进行登录管理
 * @author lx
 */
public class LoginManage {

	
	/**
	 * 检查用户名和密码
	 * @param name
	 * @param password
	 * @return 登录信息
	 */
	public static String checkPassword(String name,String password,Handler handler){
		String res = null;
		if(name.equals("master")){
			if(password.equals("123456")){
				res =  "{\"code\":1,\"msg\":\"\"}";
			}else{
				res = "{\"code\":0,\"msg\":\"密码错误\"}";
			}
		}else{
			res = "{\"code\":0,\"msg\":\"用户名不存在\"}";
		}
		return res;
	}
}
