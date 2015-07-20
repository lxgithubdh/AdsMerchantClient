package com.whut.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pgyersdk.update.PgyUpdateManager;
import com.whut.config.Constants;
import com.whut.seller.R;

public class LoginActivity extends Activity implements OnClickListener {
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		PgyUpdateManager.register(this, Constants.APP_ID);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		context = this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_login:
			Intent intent = new Intent(context,MainActivity.class);
			startActivity(intent);
			this.finish();
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 查看协议
	 */
	public void checkProtocol(View v){
		startActivity(new Intent(context,ProtocolActivity.class));
	}
}
