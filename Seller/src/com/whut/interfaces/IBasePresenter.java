package com.whut.interfaces;

import java.util.List;

import org.apache.http.NameValuePair;

import android.R.integer;

/**
 * 展示层接口
 * @author lx
 */
public interface IBasePresenter {

	//请求数据
	public void request(int requestCode);
	
	
	//显示数据
	public void response(Object data,int respondCode);
}
