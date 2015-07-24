package com.whut.activity;

import com.whut.seller.R;
import com.whut.util.SlipAction;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class WiFiManageActivity extends Activity {

	
	//用以显示的WebView
	private WebView webView;
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_wifi_manage);
		webView = (WebView)findViewById(R.id.common_web_view);
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
