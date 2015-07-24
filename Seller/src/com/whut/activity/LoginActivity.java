package com.whut.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.pgyersdk.update.PgyUpdateManager;
import com.whut.business.LoginManage;
import com.whut.config.Constants;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.SlipAction;


/**
 * 登录管理
 * @author lx
 */
public class LoginActivity extends Activity{
	
	
	private Context context;
	//用户名
	private EditText userName;
	//密码
	private EditText password;
	//登录进度框
	private ProgressDialog dialog;
	//消息处理函数
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		PgyUpdateManager.register(this, Constants.APP_ID);
		initData();
	}

	
	/**
	 * 初始化
	 */
	private void initData() {
		context = this;
		userName = (EditText)findViewById(R.id.user_name);
		password = (EditText)findViewById(R.id.password);
		dialog = new ProgressDialog(context);
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				String result = msg.getData().getString("res");
				dialog.cancel();
				if(parseResult(result)){
					startActivity(new Intent(context,MainActivity.class));
				}
				super.handleMessage(msg);
			}
			
		};
	}

	
	/**
	 * 登录
	 */
	public void logIn(View v){
		dialog.show();
		dialog.setCancelable(false);
		String userString = userName.getText().toString();
		String psdString = password.getText().toString();
		if(userString.trim().equals("")||psdString.trim().equals("")){
			dialog.cancel();
			Toast.makeText(context, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		String result = LoginManage.checkPassword(userString, psdString,handler);
		dialog.cancel();
		if(parseResult(result)){
			startActivity(new Intent(context,MainActivity.class));
			LoginActivity.this.finish();
		}
	}
	
	/**
	 * 查看协议
	 */
	public void checkProtocol(View v){
		startActivity(new Intent(context,ProtocolActivity.class));
	}
	
	
	/**
	 * 解析返回结果
	 * @param json
	 * @return 是否登录成功
	 */
	private boolean parseResult(String json){
		boolean flag = false;
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			flag = true;
		}else{
			String msg = obj.getString("msg");
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
		return flag;
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		SlipAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
