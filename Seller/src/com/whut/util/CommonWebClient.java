package com.whut.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 处理WebView交互动作
 * @author lx
 */
public class CommonWebClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return super.shouldOverrideUrlLoading(view, url);
	}
}
