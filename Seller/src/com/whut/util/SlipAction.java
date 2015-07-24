package com.whut.util;

import android.app.Activity;
import android.view.MotionEvent;

public class SlipAction {

	
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
}
