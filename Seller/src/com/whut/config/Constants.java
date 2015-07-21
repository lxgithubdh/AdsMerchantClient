package com.whut.config;

public class Constants {
	
	//http请求地址
	//跟地址
	public static final String ROOT_HTTP = "http://115.28.9.186:8899/store/service/201";
	//添加商品
	public static final String  ADD_GOODS_PATH = ROOT_HTTP + "/node-tair-web/app/mall/addGoods";
	//上传图片
	public static final String UPLOAD_PATH_IMAGE = ROOT_HTTP + "/file-repo/uploads";
	//添加优惠券
	public static final String ADD_COUPON_PATH=ROOT_HTTP+"/node-tair-web/app/mall/addCoupon";
	//获取优惠券列表
	public static final String GET_COUPON_LIST = ROOT_HTTP+"/node-tair-web/app/mall/CouponList";
	//更新优惠券
	public static final String UPDATE_COUPON_PATH = "";
	//获取商品列表
	public static final String GET_GOODS_LIST = ROOT_HTTP+"/node-tair-web/app/mall/GoodsList";
	//获取商品详情
	public static final String GET_GOODS_DETAIL = ROOT_HTTP+"/node-tair-web/app/mall/GoodsDetail";
	//更新商品信息
	public static final String UPDATE_GOODS_PATH = "";
	//获取店铺信息
	public static final String GET_STORE_PATH = "";
	//更新店铺信息
	public static final String UPDATE_STORE_PATH = "";
	//获取门户图片
	public static final String GET_PORTAL_IMAGE="";
	//更新门户图片
	public static final String UPDATE_PORTAL_IMAGE="";
	
	
	//应用ID
	public static final String APP_ID = "5d08598308b40211c7c596e3d5fc2107";
	
	public static String STORE_ID = "1";      //商铺Id
}
