package com.whut.activity;

import com.whut.seller.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserInfoActivity extends Activity{
	
	private TextView userInfoTitle;
	private RelativeLayout userPic;
	private RelativeLayout userName;
	private RelativeLayout userSex;
	private RelativeLayout userAddress;
	private RelativeLayout userAccount;
	private RelativeLayout userGrade;
	private Button btn_logout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		initView();
		
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		userInfoTitle = (TextView) findViewById(R.id.user_info_title);
		userPic = (RelativeLayout) findViewById(R.id.user_info_pic);
		userName = (RelativeLayout) findViewById(R.id.user_info_name);
		userAccount = (RelativeLayout) findViewById(R.id.user_info_account);
		userAddress = (RelativeLayout) findViewById(R.id.user_info_address);
		userGrade = (RelativeLayout) findViewById(R.id.user_info_grade);
		userSex = (RelativeLayout) findViewById(R.id.user_info_sex);
		btn_logout = (Button) findViewById(R.id.btn_user_logout);
		Intent i = getIntent();
		String flag = i.getStringExtra("userFlag");
		if(flag.equals("1")){
			userGrade.setVisibility(View.GONE);
		}else if(flag.equals("0")){
			userInfoTitle.setText("会员资料");
			btn_logout.setVisibility(View.GONE);
		}
		
	}

}
