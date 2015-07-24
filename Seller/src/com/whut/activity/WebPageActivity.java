package com.whut.activity;

import com.whut.business.WebPageClient;
import com.whut.seller.R;
import com.whut.util.SlipAction;
import com.whut.util.WVJBWebViewClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


/**
 * 显示HTML5
 * @author lx
 */
public class WebPageActivity extends Activity {

	
	@SuppressLint("SetJavaScriptEnabled") 
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_web_page);
		
		WebView webView = (WebView)findViewById(R.id.common_web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		WVJBWebViewClient client = new WebPageClient(webView,WebPageActivity.this);
		client.enableLogging();
		webView.setWebViewClient(client);
		
		webView.loadUrl("http://192.168.2.201:18002/1");
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		SlipAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
