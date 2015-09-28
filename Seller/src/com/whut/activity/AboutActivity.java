package com.whut.activity;

import com.whut.seller.R;
import com.whut.util.BackAction;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * 关于
 * @author lx
 */
public class AboutActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		((TextView)findViewById(R.id.activity_title)).setText("关于商户端");
	}

	
	/**
	 * 返回
	 * @param v
	 */
	public void onBack(View v) {
		this.finish();
	}
	

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
