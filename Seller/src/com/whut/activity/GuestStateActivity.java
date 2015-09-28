package com.whut.activity;

import java.io.File;

import com.whut.seller.R;
import com.whut.util.BackAction;
import com.whut.util.CommonWebClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * 查看客户位置信息
 * @author lx
 */
public class GuestStateActivity extends Activity {

	//用于显示结果的WebView
	private WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled") 
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_webview);
		((TextView)findViewById(R.id.activity_title)).setText("客流情况");
		webView = (WebView)findViewById(R.id.common_web_view);
		webView.clearCache(true);
		clearWebViewCache();
		webView.loadUrl("http://219.153.20.141/customer_flow/demo/#/");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.setWebViewClient(new CommonWebClient());
	}
	
	
	 /**
     * 清除WebView缓存
     */ 
    public void clearWebViewCache(){ 
           
        //清理Webview缓存数据库 
        try { 
            deleteDatabase("webview.db");  
            deleteDatabase("webviewCache.db"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
           
        //WebView 缓存文件 
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+"/webcache"); 
           
        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache"); 
           
        //删除webview 缓存目录 
        if(webviewCacheDir.exists()){ 
            deleteFile(webviewCacheDir); 
        } 
        //删除webview 缓存 缓存目录 
        if(appCacheDir.exists()){ 
            deleteFile(appCacheDir); 
        } 
    } 
       
    
    /**
     * 递归删除 文件/文件夹
     * 
     * @param file
     */ 
    public void deleteFile(File file) { 
           
        if (file.exists()) { 
            if (file.isFile()) { 
                file.delete(); 
            } else if (file.isDirectory()) { 
                File files[] = file.listFiles(); 
                for (int i = 0; i < files.length; i++) { 
                    deleteFile(files[i]); 
                } 
            } 
            file.delete(); 
        } 
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
