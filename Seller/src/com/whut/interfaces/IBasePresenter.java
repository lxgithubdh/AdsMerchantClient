package com.whut.interfaces;

/**
 * 展示层接口
 * @author lx
 */
public interface IBasePresenter {

	//请求数据
	public void request(int requestCode);
	
	
	//显示数据
	public void response(String data,int respondCode);
}
