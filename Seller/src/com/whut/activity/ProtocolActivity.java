package com.whut.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.whut.seller.R;
import com.whut.util.BackAction;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 协议界面
 * @author lx
 */
public class ProtocolActivity extends Activity {

	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_protocol);
		TextView protocolText = (TextView)findViewById(R.id.protocol);
		String msg = getProtocol();
		protocolText.setText(msg);
		
	}
	
	
	/**
	 * 获取协议条款信息		
	 */
	private String getProtocol(){
		String result = null;
		InputStream is = this.getResources().openRawResource(R.raw.protocol);
		try {
		InputStreamReader isr = new InputStreamReader(is,"utf-8");
		BufferedReader reader = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while((line=reader.readLine())!=null){
			buffer.append(line);
			buffer.append("\n");
		}
		result = buffer.toString();
		} catch (Exception e) {
			result = "读取错误！";
		}
		return result;
	}
	

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
