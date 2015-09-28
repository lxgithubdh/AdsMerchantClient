package com.whut.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * SharedPreferences辅助类
 * @author lx
 */
public class SharedPreferenceHelper {

	private static SharedPreferences preference;
	
	
	/**
	 * 设置SharedPreferences
	 * @param p
	 */
	public static void setSharedPreferences(SharedPreferences p){
		preference = p;
	}
	
	
	/**
	 * 添加键值对
	 * @param key
	 * @param value
	 */
	public static void setValue(String key,String value){
		Editor editor = preference.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	
	/**
	 * 获取值
	 * @param key 
	 * @return
	 */
	public static String getValue(String key,String defaultString){
		return preference.getString(key, defaultString);
	}
}
