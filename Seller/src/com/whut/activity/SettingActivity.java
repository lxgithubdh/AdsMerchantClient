package com.whut.activity;

import com.whut.util.BackAction;

import android.app.Activity;
import android.view.MotionEvent;

/**
 * 设置界面
 * @author lx
 */
public class SettingActivity extends Activity {



	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
	
}
