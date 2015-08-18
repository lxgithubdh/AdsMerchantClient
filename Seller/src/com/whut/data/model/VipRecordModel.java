package com.whut.data.model;

import java.util.ArrayList;

/**
 * vip进店记录信息
 * @author lx
 */
public class VipRecordModel {

	//记录日期
	private String recordDate;
	//vip用户列表
	private ArrayList<VipDetailModel> vipList = new ArrayList<VipDetailModel>();
	
	
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public ArrayList<VipDetailModel> getVipList() {
		return vipList;
	}
	public void setVipList(ArrayList<VipDetailModel> vipList) {
		this.vipList = vipList;
	}
}
