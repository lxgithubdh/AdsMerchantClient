package com.whut.activity;

import com.whut.seller.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;


/**
 * 欢迎界面
 * @author lx
 */
public class WelcomActivity extends Activity {

	
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_welcom);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = null;
				if(checkFirstLaunche()){
					intent = new Intent(WelcomActivity.this,PrologueActivity.class);
				}else{
					intent = new Intent(WelcomActivity.this,LoginActivity.class);
				}
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
				WelcomActivity.this.finish();
			}
		}, 2000);                       //2秒钟后跳转
	}
	
	
	/**
	 * 判断是否初次启动
	 * @return  
	 */
	private boolean checkFirstLaunche(){
		SharedPreferences preferences = this.getSharedPreferences("config", Activity.MODE_PRIVATE);
		boolean flag = preferences.getBoolean("isFirst", true);
		if(flag){
			Editor editor = preferences.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}
		return flag;
	}
}
