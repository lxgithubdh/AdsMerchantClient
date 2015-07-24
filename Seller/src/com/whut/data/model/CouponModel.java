package com.whut.data.model;

import java.io.Serializable;


/**
 * 优惠券模型，存储优惠券信息
 * @author lx
 */
public class CouponModel implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
    private String cId;     //优惠券id
    private String title;    //标题
    private String desc;   //描述
    private String startTime;          //开始时间
    private String endTime;           //结束时间
    private int type;           //优惠券类型
    private boolean isAllow;         //是否允许使用
    private String imageUrl;         //图片链接
    
    
    
    
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isAllow() {
		return isAllow;
	}
	public void setAllow(boolean isAllow) {
		this.isAllow = isAllow;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

    
    
}
