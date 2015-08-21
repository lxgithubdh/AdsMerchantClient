package com.whut.interfaces;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * 展示层接口
 * @author lx
 */
public interface IBasePresenter {

	//获取数据
	public void request(String url , List<NameValuePair> pairs,String... params);
	
	//保存数据
	public void saveData(Object data);
	
	//显示数据
	public void showData(Object data);
}
