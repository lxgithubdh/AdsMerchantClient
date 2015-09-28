package com.whut.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.pgyersdk.Pgy;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.CouponModel;
import com.whut.imageloader.ImageLoader;
import com.whut.seller.R;
import com.whut.util.AsyncPost;
import com.whut.util.AsyncUploadFile;
import com.whut.util.ImageUtil;
import com.whut.util.JsonUtils;
import com.whut.util.PickDateDialog;
import com.whut.util.SelectImage;
import com.whut.util.BackAction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 优惠券详情
 * @author lx
 */
public class PreferentialDetailActivity extends Activity{
	
	
	    //优惠券图片
		private ImageView image;
		//选择图片
		private SelectImage selectImage;
		//优惠券图片链接
		private String imageUrl;
		//是否允许使用
		private String isAllow="true";
		//处理消息循环
		private Handler handler;
		//进度条
		private ProgressDialog dialog;
		//是否更换图片
		private boolean changeImage = false;
		//是否可以提交
		private boolean canSubmit = true;
		//优惠券类型
		private  int couponType = 0;
		//优惠券类型列表
		private Spinner type;
		private List<NameValuePair> params = new ArrayList<NameValuePair>() ;  //参数列表
		private Context context;
		
		private EditText desc;
		private EditText title;
		private EditText startTime;
		private EditText endTime;
		private RadioButton canAllow;
		private String cId;                //优惠券id
		
		
		/**
		 * 初始化方法
		 */
		protected void onCreate(Bundle bundle){
			super.onCreate(bundle);
			setContentView(R.layout.activity_preferential_detail);
			
			((TextView)findViewById(R.id.activity_title_complete)).setText("优惠券详情");
			context = this;
			Pgy.init(context, Constants.APP_ID);
	        dialog = new ProgressDialog(context);
			image = (ImageView)findViewById(R.id.preferential_info_image);
			selectImage = new SelectImage(context);
			RadioGroup group = (RadioGroup)findViewById(R.id.preferential_info_is_allow);
			desc = (EditText)findViewById(R.id.preferential_info_desc);
			title = (EditText)findViewById(R.id.preferential_info_title);
			type = (Spinner)findViewById(R.id.preferential_info_type);
			startTime = (EditText)findViewById(R.id.preferential_info_start_time);
			endTime = (EditText)findViewById(R.id.preferential_info_end_time);
			
			CouponModel coupon = (CouponModel) getIntent().getExtras().get("coupon");
			cId = coupon.getcId();
			imageUrl = coupon.getImageUrl();
			desc.setText(coupon.getDesc());
			title.setText(coupon.getTitle());
			type.setSelection(coupon.getType());
			startTime.setText(coupon.getStartTime());
			endTime.setText(coupon.getEndTime());
			if(coupon.isAllow()){
				canAllow = (RadioButton)findViewById(R.id.preferential_info_can_allow);
				canAllow.setChecked(true);
				isAllow = "true";
			}else{
				canAllow = (RadioButton)findViewById(R.id.preferential_info_can_not_allow);
				canAllow.setChecked(true);
				isAllow = "false";
			}
			ImageLoader.getInstances().displayImage(imageUrl, image);
			
			group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if(checkedId==R.id.preferential_info_can_allow){
						isAllow = "true";
					}else{
						isAllow = "false";
					}
				}
			});
			 
			type.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					couponType = position;
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
			
			handler = new Handler(){
				@Override
				public void handleMessage(Message msg){
					String result = msg.getData().getString("res");
					switch(msg.what){
					case 0:                      //更新优惠券信息
						onComplete(result);
						break;
					case 1:                       //图片上传
						imageUrl = JsonUtils.parseUploadImage(result, dialog, context);
						if(imageUrl!=null){
							submitCoupon();
						}
						break;
					default :
						break;
					}
				}
			};
			
			startTime.setInputType(InputType.TYPE_NULL);
			startTime.setOnFocusChangeListener(new PickDateDialog(context, startTime));
			endTime.setInputType(InputType.TYPE_NULL);
			endTime.setOnFocusChangeListener(new PickDateDialog(context, endTime));
		}
		
		
		/**
		 * 点击ImageView事件处理函数
		 * @param v(ImageView)
		 */
		public void selectImage(View v){
			selectImage.selectWay();
		}
		
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			Bitmap temp = null;
			temp = selectImage.getImage(requestCode, resultCode, data, false);
			if(temp!=null){
				image.setImageBitmap(temp);
				changeImage = true;
			}
			super.onActivityResult(requestCode, resultCode, data);
		}

		
		/**
		 * 点击完成按钮事件处理函数
		 * @param v(Button)
		 */
		public void onSubmit(View v){
			Bitmap bitmap  = ImageUtil.getBitmap(image);
	        checkMsg();
	        if(canSubmit){
	        	dialog.show();
	        	dialog.setCancelable(false);
	        	if(changeImage){
	    			AsyncUploadFile uploadImage= new AsyncUploadFile(bitmap,handler);
	    			uploadImage.execute();
	    		}else{
	    			submitCoupon();
	    		}
			}else{
				Toast.makeText(context, "请完善重要信息！", Toast.LENGTH_LONG).show();
			}
		}
		
		
		/**
		 * 处理上传商品结果信息
		 * @param json string
		 */
		private void onComplete(String json){
			JsonUtils.parseAddOrUpdateResult(json, "更新", dialog, context);
			Intent intent = new Intent(context,GoodsListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		
		
		
		/**
		 * 检查填写信息
		 */
		private void checkMsg(){
			String temp = null;
			canSubmit = true;
			params.clear();
			params.add(new BasicNameValuePair("sId", Constants.STORE_ID));
			temp =title.getText().toString();
			if(temp.equals("")){
				canSubmit = false;
			}
			params.add(new BasicNameValuePair("title", temp));
			temp = desc.getText().toString();
			if(temp.equals("")){
				canSubmit = false;
			}
			params.add(new BasicNameValuePair("desc", temp));
			temp = startTime.getText().toString();
			if(temp.equals("")){
				temp = new Date().toString();
			}
			params.add(new BasicNameValuePair("startTime", temp));
			temp = endTime.getText().toString();
			if(temp.equals("")){
				temp = new Date().toString();
			}
			params.add(new BasicNameValuePair("endTime", temp));
			params.add(new BasicNameValuePair("type", String.valueOf(couponType)));
			
			params.add(new BasicNameValuePair("isReturnAnytime", isAllow));
		    params.add(new BasicNameValuePair("imageUrl",imageUrl));
		}
		/**
		 * 提交数据
		 */
		private void submitCoupon(){
			AsyncPost asyncHttpPost = new AsyncPost(RequestParam.UPDATE_COUPON_PATH, params,handler,0);     //发起Post请求
			asyncHttpPost.execute();
		}
		
		
		/**
		 * 返回
		 * @param v
		 */
		public void onBack(View v){
			finish();
		}


		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			BackAction.slipToExit(this, ev);
			return super.dispatchTouchEvent(ev);
		}
}
