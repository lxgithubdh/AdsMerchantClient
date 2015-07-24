package com.whut.activity;

import com.whut.seller.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;


/**
 * 关于
 * @author lx
 */
public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	
	/**
	 * 返回
	 * @param v
	 */
	public void onBack(View v) {
		this.finish();
	}
}
