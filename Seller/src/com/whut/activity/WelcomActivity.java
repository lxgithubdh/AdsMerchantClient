package com.whut.activity;

import com.whut.seller.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
				//if(checkFirstLaunche()){
					//intent = new Intent(WelcomActivity.this,PrologueActivity.class);
				//}else{
					intent = new Intent(WelcomActivity.this,LoginActivity.class);
				//}
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
		boolean isFirst = preferences.getBoolean("isFirst", true);
		String oldVersion = preferences.getString("version", "0.0.0");
		String newVersion = getVersion();
		boolean result = isFirst||!newVersion.equals(oldVersion);
		if(result){
			Editor editor = preferences.edit();
			editor.putBoolean("isFirst", false);
			editor.putString("version", newVersion);
			editor.commit();
		}
		return result;
	}
	
	
	/**
	 * 获取应用版本号
	 * @return 版本号
	 */
	private String getVersion(){
		String version = null;
		try{
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			version = info.versionName;
		}catch(Exception e){
			version = "0.0.0";
		}
		return version;
	}
}
