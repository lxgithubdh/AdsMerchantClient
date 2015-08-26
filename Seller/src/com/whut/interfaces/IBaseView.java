package com.whut.interfaces;

/**
 * 视图层接口
 * @author lx
 */
public interface IBaseView {

	//获取view信息
	public Object getInfo(int code);
	
	
	//设置view信息
	public void setInfo(Object obj,int code);
}
