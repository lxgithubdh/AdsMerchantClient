package com.whut.activity;

import com.whut.seller.R;
import com.whut.util.BackAction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * 设置界面
 * @author lx
 */
public class SettingActivity extends Activity {

	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_setting);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
	
	public void getUserInfo(View v){
		Intent i = new Intent(this,UserInfoActivity.class);
		i.putExtra("userFlag", "1");
		startActivity(i);
	}
	
}
