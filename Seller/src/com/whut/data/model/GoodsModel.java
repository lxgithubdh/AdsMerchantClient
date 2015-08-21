package com.whut.data.model;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 商品信息类，存储商品信息
 * @author lx
 */
public class GoodsModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String gid; // 商品id号
	private String title; // 商品名
	private String desc; // 商品描述
	private String imageUrl; // 图片地址
	private int inventory; // 库存
	private double originalPrice; // 原价
	private double currentPrice; // 现价
	private boolean isReturnAnytime;//是否支持随时退款
	private int catgory;//类别
	private String notice;//注意信息
	private String buyDetail;//购买详情
	private int sales;  //销量

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isReturnAnytime() {
		return isReturnAnytime;
	}

	public void setReturnAnytime(boolean isReturnAnytime) {
		this.isReturnAnytime = isReturnAnytime;
	}

	public int getCatgory() {
		return catgory;
	}

	public void setCatgory(int catgory) {
		this.catgory = catgory;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getBuyDetail() {
		return buyDetail;
	}

	public void setBuyDetail(String buyDetail) {
		this.buyDetail = buyDetail;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
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

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public double getOriginalPrice() {
		return new BigDecimal(originalPrice)
				.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return new BigDecimal(currentPrice).setScale(1,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

}