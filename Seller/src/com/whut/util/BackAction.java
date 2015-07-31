package com.whut.util;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 处理返回事件
 * @author lx
 */
public class BackAction {

	
	//起始位置横坐标
	private static float startX = 0;
	
	
	/**
	 * 滑动退出
	 * @param activity
	 * @param 触摸事件信息
	 */
	public static void slipToExit(Activity activity,MotionEvent event){
		switch (event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			if((event.getX()-startX)>250){
				activity.finish();
			}
			break;
		}
	}
	
	/**
	 * 处理WebView回退事件
	 * @param keyCode
	 * @param webView
	 * @return 是否处理
	 */
	public static boolean webViewBack(int keyCode,WebView webView){
		if((keyCode==KeyEvent.KEYCODE_BACK)&&webView.canGoBack()){
			webView.goBack();              //返回上一页
			return true;
		}
		return false;
	}
}
