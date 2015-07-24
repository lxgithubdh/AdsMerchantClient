package com.whut.activity;

import com.whut.util.SlipAction;

import android.app.Activity;
import android.view.MotionEvent;

public class SettingActivity extends Activity {



	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		SlipAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
	
}
