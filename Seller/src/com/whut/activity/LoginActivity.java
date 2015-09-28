package com.whut.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.pgyersdk.update.PgyUpdateManager;
import com.whut.component.service.VipNoticeService;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.UserModel;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.LoginPresenter;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.BackAction;


/**
 * 登录界面
 * @author lx
 */
public class LoginActivity extends Activity implements IBaseView{
	
	
	private Context context;
	//用户名
	private EditText userName;
	//密码
	private EditText password;
	//登录进度框
	private ProgressDialog dialog;
	//登录管理器
	private LoginPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		PgyUpdateManager.register(this, Constants.APP_ID);
		presenter = new LoginPresenter(this);
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
	}

	
	/**
	 * 登录
	 */
	public void logIn(View v){
		presenter.request(RequestParam.REQUEST_QUERY);
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
			Toast.makeText(context, "登录失败!", Toast.LENGTH_SHORT).show();
		}
		return flag;
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}


	@Override
	public Object getInfo(int code) {
		dialog.show();
		dialog.setCancelable(false);
		String userString = userName.getText().toString();
		String psdString = password.getText().toString();
		if(userString.trim().equals("")||psdString.trim().equals("")){
			dialog.cancel();
			Toast.makeText(context, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
			return null;
		}
		UserModel user = new UserModel();
		user.setUserName(userString);
		user.setPassword(psdString);
		return user;
	}


	@Override
	public void setInfo(Object obj, int code) {
		dialog.cancel();
		if(parseResult((String)obj)){
			startActivity(new Intent(context,MainActivity.class));
			Intent intent = new Intent(context,VipNoticeService.class);
			startService(intent);
			LoginActivity.this.finish();
		}
	}
}
