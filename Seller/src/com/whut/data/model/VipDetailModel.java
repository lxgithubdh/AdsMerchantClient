package com.whut.data.model;

/**
 * vip详细信息
 * @author lx
 */
public class VipDetailModel {

	//头像图片链接
	private String portraitImageUrl;
	//vip用户名
	private String vipUserName;
	//vip用户标签
	private String vipTag;
	//vip进店时间
	private String enterTime;
	
	
	public String getPortraitImageUrl() {
		return portraitImageUrl;
	}
	public void setPortraitImageUrl(String portraitImageUrl) {
		this.portraitImageUrl = portraitImageUrl;
	}
	public String getVipUserName() {
		return vipUserName;
	}
	public void setVipUserName(String vipUserName) {
		this.vipUserName = vipUserName;
	}
	public String getVipTag() {
		return vipTag;
	}
	public void setVipTag(String vipTag) {
		this.vipTag = vipTag;
	}
	public String getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
}
