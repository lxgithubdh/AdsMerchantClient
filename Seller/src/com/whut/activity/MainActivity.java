package com.whut.activity;

import java.util.Random;

import com.whut.seller.R;
import com.whut.util.BadgeView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * 主界面
 * @author lx
 */
public class MainActivity extends Activity{

	//上下文
	private Context context;
	//显示VIP数量的浮标
	private ImageView image;
	//浮标数字
	private BadgeView badge;
	//vip信息
	private LinearLayout vipInfo;
	
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
		context = this;
		image = (ImageView)findViewById(R.id.vip_suspension);
		vipInfo = (LinearLayout)findViewById(R.id.vip_info);
		vipInfo.setVisibility(View.GONE);
		badge = new BadgeView(context, image);
	}

	
	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()){
		case  R.id.goto_preferential_manage:             //优惠管理
			intent = new Intent(context,PreferentialManagerActivity.class);
			startActivity(intent);
			break;
		case  R.id.goto_guest_flow:                                //客流管理
			intent = new Intent(context,GuestStateActivity.class);
			startActivity(intent);
			break;
		case  R.id.goto_store_manage:             //店铺管理
			intent = new Intent(context,StoreManageActivity.class);
			startActivity(intent);
			break;
		case  R.id.goto_order_manage:             //订单管理
			intent = new Intent(context,OrderManageActivity.class);
			startActivity(intent);
			break;
		case  R.id.goto_member_manage:             //会员管理
			intent = new Intent(context,MemberManagerActivity.class);
			startActivity(intent);
			break;
		case  R.id.goto_wifi_manage:             //wifi管理
			intent = new Intent(context,WiFiManageActivity.class);
			startActivity(intent);
			break;
		case  R.id.goto_comment:             //优惠管理
			intent = new Intent(context,CommentManagerActivity.class);
			startActivity(intent);
			break;
		case  R.id.goto_ads_manage:             //广告管理
			intent = new Intent(context,AdsManagerActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * 显示VIP信息详情
	 */
	public void showDetail(View v){
		/*badge.hide();
		int number = new Random().nextInt(100);
		badge.setText(String.valueOf(number));
		badge.show();
		Animation popup = AnimationUtils.loadAnimation(context, R.anim.vip_info_popup);
		vipInfo.setVisibility(View.VISIBLE);
		vipInfo.startAnimation(popup);
		popup.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Animation withdraw = AnimationUtils.loadAnimation(context, R.anim.vip_info_withdraw);
				try {
					new Thread().sleep(1000);
				} catch (InterruptedException e) {}
				vipInfo.startAnimation(withdraw);
				withdraw.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {}
					
					@Override
					public void onAnimationRepeat(Animation animation) {}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						vipInfo.setVisibility(View.GONE);
					}
				});
			}
		});*/
		startActivity(new Intent(context,VipRecordActivity.class));
	}
	
	
	/**
	 * 显示我的信息
	 */
	public void showInfo(View v){
		startActivity(new Intent(context,SettingActivity.class));
	}
}
