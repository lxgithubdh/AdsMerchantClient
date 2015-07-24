package com.whut.activity;

import com.whut.seller.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class GuestStateActivity extends Activity {

	//用于显示结果的WebView
	private WebView webView;
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_guest_state);
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
}
