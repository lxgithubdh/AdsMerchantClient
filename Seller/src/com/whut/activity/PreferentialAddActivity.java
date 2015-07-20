package com.whut.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.pgyersdk.Pgy;
import com.whut.config.Constants;
import com.whut.seller.R;
import com.whut.util.AsyncHttpPost;
import com.whut.util.AsyncUploadFile;
import com.whut.util.ImageUtil;
import com.whut.util.JsonUtils;
import com.whut.util.PickDateDialog;
import com.whut.util.SelectImage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


/**
 * 添加优惠券
 * @author lx
 */
public class PreferentialAddActivity extends Activity {
	
	    
	    //优惠券图片
		private ImageView image;
		//优惠券图片链接
		private String imageUrl;
		//是否可以使用
		private String isAllow="true";
		//处理消息循环
		private Handler handler;
		//进度条
		private ProgressDialog dialog;
		//是否可以提交
		private boolean canSubmit = true;
		//选择图片
		private SelectImage selectImage;
		//起始时间
		private EditText startDate;
		//结束时间
		private EditText endDate;
		private List<NameValuePair> params = new ArrayList<NameValuePair>() ;  //参数列表
		private Context context;
		
		
		/**
		 * 初始化方法
		 */
		protected void onCreate(Bundle bundle){
			super.onCreate(bundle);
			setContentView(R.layout.activity_preferential_add);
			
			context = this;
			Pgy.init(context, Constants.APP_ID);
			image = (ImageView)findViewById(R.id.preferential_info_image);
	        dialog = new ProgressDialog(context);
	        selectImage = new SelectImage(context);
	        startDate = (EditText)findViewById(R.id.preferential_info_start_time);
	        endDate = (EditText)findViewById(R.id.preferential_info_end_time);
			RadioGroup group = (RadioGroup)findViewById(R.id.preferential_info_is_allow);
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
			 
			handler = new Handler(){
				@Override
				public void handleMessage(Message msg){
					String result = msg.getData().getString("res");
					switch(msg.what){
					case 0:                      //优惠券上传
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
			
			startDate.setInputType(InputType.TYPE_NULL);
			startDate.setOnFocusChangeListener(new PickDateDialog(context, startDate));
			endDate.setInputType(InputType.TYPE_NULL);
			endDate.setOnFocusChangeListener(new PickDateDialog(context, endDate));
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
			Bitmap temp = selectImage.getImage(requestCode, resultCode, data, false);
			if(temp!=null){
				image.setImageBitmap(temp);
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
	            AsyncUploadFile uploadImage= new AsyncUploadFile(bitmap,handler);
	    		uploadImage.execute();
			}else{
				Toast.makeText(context, "请完善重要信息！", Toast.LENGTH_LONG).show();
			}
		}
		
		
		/**
		 * 处理上传商品结果信息
		 * @param json string
		 */
		private void onComplete(String json){
			JsonUtils.parseAddOrUpdateResult(json, "添加", dialog, context);
			Intent intent = new Intent(context,GoodsListActivity.class);
			startActivity(intent);
		}
		
		
		/**
		 * 检查输入信息
		 */
		private void checkMsg(){
			String temp = null;
			canSubmit = true;
			params.clear();
			params.add(new BasicNameValuePair("sId", Constants.STORE_ID));
			temp =((EditText)this.findViewById(R.id.preferential_info_title)).getText().toString();
			if(temp.equals("")){
				canSubmit = false;
			}
			params.add(new BasicNameValuePair("title", temp));
			temp = ((EditText)this.findViewById(R.id.preferential_info_desc)).getText().toString();
			if(temp.equals("")){
				canSubmit = false;
			}
			params.add(new BasicNameValuePair("desc", temp));
			temp = ((EditText)this.findViewById(R.id.preferential_info_start_time)).getText().toString();
			if(temp.equals("")){
				temp = new Date().toString();
			}
			params.add(new BasicNameValuePair("startTime", temp));
			temp = ((EditText)this.findViewById(R.id.preferential_info_end_time)).getText().toString();
			if(temp.equals("")){
				temp = new Date().toString();
			}
			params.add(new BasicNameValuePair("endTime", temp));
			temp =( (EditText)this.findViewById(R.id.preferential_info_type)).getText().toString();
			if(temp.equals("")){
				temp = "无";
			}
			params.add(new BasicNameValuePair("type", temp));
			
			params.add(new BasicNameValuePair("isAllow", isAllow));
		    //params.add(new BasicNameValuePair("imageUrl",imageUrl));
		}
		
		
		/**
		 * 提交数据
		 */
		private void submitCoupon(){
			AsyncHttpPost asyncHttpPost = new AsyncHttpPost(Constants.ADD_COUPON_PATH, params,handler,0);     //发起Post请求
			asyncHttpPost.execute();
		}
		
		
		/**
		 * 返回
		 * @param v
		 */
		public void onBack(View v){
			finish();
		}
}
