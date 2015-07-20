package com.whut.business;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.whut.activity.AboutActivity;
import com.whut.util.WVJBWebViewClient;
import com.whut.util.WVJBWebViewClient.WVJBHandler;
import com.whut.util.WVJBWebViewClient.WVJBResponseCallback;


/**
 * 负责网页交互
 * @author lx
 */
public class WebPageClient extends WVJBWebViewClient {
	
    
	public WebPageClient(WebView webView,Context context) {
		super(webView,new WebHandler(context));
		
		enableLogging();
	}
	
	
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return super.shouldOverrideUrlLoading(view, url);
	}
}


/**
 * 处理请求
 * @author lx
 */
class WebHandler implements WVJBHandler{

	
	//运行上下文
	private Context context;
	
	
	public WebHandler(Context context){
		this.context = context;
	}
	@Override
	public void request(Object data, WVJBResponseCallback callback) {
		Log.i("request",String.valueOf(data));
		context.startActivity(new Intent(context,AboutActivity.class));
		callback.callback(String.valueOf(data));
	}
}
