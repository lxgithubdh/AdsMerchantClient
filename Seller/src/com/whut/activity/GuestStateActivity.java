package com.whut.activity;

import com.whut.seller.R;
import com.whut.util.SlipAction;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class GuestStateActivity extends Activity {

	//用于显示结果的WebView
	private WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled") protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_guest_state);
		webView = (WebView)findViewById(R.id.common_web_view);
		webView.loadUrl("http://192.168.2.174:8080/demo/#/");
		webView.getSettings().setJavaScriptEnabled(true);
	}
	
	/**
	 * 返回
	 */
	public void onBack(View v){
		this.finish();
	}
	
	
	/**
	 * 刷新
	 */
	public void onRefresh(View e){
		webView.reload();
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		SlipAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
