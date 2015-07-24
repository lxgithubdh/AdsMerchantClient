package com.whut.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * 监听应用安装事件
 * @author lx
 */
public class InstallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		/*SharedPreferences preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isFirst", true);
		editor.commit();*/
		Log.i("log",intent.getAction());
	}

}
