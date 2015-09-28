package com.whut.activity;

import com.whut.seller.R;
import com.whut.util.BackAction;
import com.whut.util.CommonWebClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * 查看连接AP用户信息
 * @author lx
 */
public class WiFiManageActivity extends Activity {

	
	//用以显示的WebView
	private WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled") 
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_webview);
		((TextView)findViewById(R.id.activity_title)).setText("WiFi管理");
		webView = (WebView)findViewById(R.id.common_web_view);
		webView.loadUrl("http://219.153.20.141/apControl/index.html");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new CommonWebClient());
	}
	
	
	/**
	 * 返回
	 */
	public void onBack(View v){
		this.finish();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(BackAction.webViewBack(keyCode, webView)){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
