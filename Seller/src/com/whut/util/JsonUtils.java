package com.whut.util;


import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

/**
 * 用于解析Json数据
 * @author lx
 */
public class JsonUtils {

	public static boolean isGoodJson(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		try {
			JSONObject.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * 解析Json数据
	 * @param json
	 * @return 解析成功返回Object，失败返回null
	 */
	public static JSONObject parseJson(String json){
		if(JsonUtils.isGoodJson(json)){
			return JSONObject.parseObject(json);
		}else{
			return null;
		}	
	}
	
	
	/**
	 * 解析获取上传图片返回的图片链接
	 * @param json数据，进度对话框，运行上下文
	 * @return  图片链接，错误则为null
	 */
	public static String parseUploadImage(String json,Dialog dialog,Context context){
		String url = null;
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			url = obj.getString("imageUrl");
		}else{
			dialog.cancel();
			Toast.makeText(context, "上传图片失败！", Toast.LENGTH_LONG).show();
		}
		return url;
	}
	
	
	
	/**
	 * 解析添加和更新结果信息
	 * @param json  结果数据
	 * @param msg  操作信息
	 * @param context 上下文
	 */
	public static void parseAddOrUpdateResult(String json,String msg,Dialog dialog,Context context){
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			msg += "成功！";
		}else{
			msg += "失败！";
		}
		dialog.cancel();
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}
