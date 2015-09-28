package com.whut.activity;

import com.whut.business.PortalImageManage;
import com.whut.seller.R;
import com.whut.util.SelectImage;
import com.whut.util.BackAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 显示门户页面以及对其进行操作
 * @author lx
 */
public class PortalImageActivity extends Activity {

	
	//管理图片
	private PortalImageManage manage;
	//是否选择图片
	private boolean isChange;
	//程序上下文
	private Context context;
	//选择图片
	private SelectImage selectImage;
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_portal_image);
		((TextView)findViewById(R.id.activity_title_complete)).setText("门户图片");
		ImageView image = (ImageView)findViewById(R.id.portal_image);
		context = PortalImageActivity.this;
		manage = new PortalImageManage(image,context);          //实例化管理类
		selectImage = new SelectImage(context);
		
		init();
	}
	
	
	/**
	 * 初始化
	 */
	private void init(){
		isChange = false;               //默认为改变图片
		manage.initImageView();
	}
	
	
	/**
	 * 返回
	 */
	public void onBack(View v){
		this.finish();
	}
	
	
	/**
	 * 提交更改操作
	 */
	public void onSubmit(View v){
		if(isChange){
			manage.saveChange();
			isChange = false;
		}else{
			Toast.makeText(context, "请先选择图片！", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	/**
	 * 选择图片
	 */
	public void selectImage(View v){
		selectImage.selectWay();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap temp = null;
	    temp = selectImage.getImage(requestCode, resultCode, data, true);
	    if(temp!=null){
	    	manage.setImage(temp);
	    	isChange = true;
	    }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		init();
		super.onNewIntent(intent);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
