package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.pgyersdk.Pgy;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.seller.R;
import com.whut.util.AsyncPost;
import com.whut.util.AsyncUploadFile;
import com.whut.util.ImageUtil;
import com.whut.util.JsonUtils;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 店铺信息管理
 * @author lx
 */
public class StoreManageActivity extends Activity {

	
		//商品图片链接
		private String imageUrl;
		//处理消息循环
		private Handler handler;
		//进度条
		private ProgressDialog dialog;
		//是否更换图片
		private boolean changeImage = false;
		//选择图片
		private SelectImage selectImage;
		//是否可以提交
		private boolean canSubmit = true;
		private List<NameValuePair> params = new ArrayList<NameValuePair>() ;  //参数列表
		private Context context;
	
	    private ImageView image;                 //店铺图片
	    private EditText title;                         //店铺名称
	    private EditText name;                      //店主姓名
	    private EditText phone;                     //电话号码
	    private EditText qq;                           //qq号码
	    private EditText wechat;                   //微信号
	    private EditText address;                  //地址
	    private EditText email;                      //电子邮箱
	    private EditText notice;                     //公告信息
	
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_store_manage);
		
		((TextView)findViewById(R.id.activity_title_complete)).setText("店铺管理");
		//init();
		//getInitData();
		//setInitData(new JSONObject());
	}
	
	
	/**
	 * 初始化控件引用
	 */
	private void init(){
		image = (ImageView)this.findViewById(R.id.store_manage_image);
		title = (EditText)this.findViewById(R.id.store_manage_title);
		name = (EditText)this.findViewById(R.id.store_manage_name);
		phone = (EditText)this.findViewById(R.id.store_manage_phone);
		qq = (EditText)this.findViewById(R.id.store_manage_qq);
		wechat = (EditText)this.findViewById(R.id.store_manage_wechat);
		address = (EditText)this.findViewById(R.id.store_manage_address);
		email = (EditText)this.findViewById(R.id.store_manage_email);
		notice = (EditText)this.findViewById(R.id.store_manage_notice);
		context = this;
		//dialog = new ProgressDialog(context);
		selectImage = new SelectImage(context);
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				String result = msg.getData().getString("res");
				switch(msg.what){
				case 0:                      //更新店铺信息
					onComplete(result);
					break;
				case 1:                       //图片上传
					imageUrl = JsonUtils.parseUploadImage(result, dialog, context);
					if(imageUrl!=null){
						submitStoreMsg();
					}
					break;
				case 2:                     //获取数据
					onGetData(result);
					break;
				default :
					break;
				}
			}
		};
		
		Pgy.init(context, Constants.APP_ID);
	}
	
	
	/**
	 * 返回
	 * @param v
	 */
	public void onBack(View v){
		this.finish();
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
        	//dialog.show();
        	//dialog.setCancelable(false);
        	if(changeImage){
    			AsyncUploadFile uploadImage= new AsyncUploadFile(bitmap,handler);
    			uploadImage.execute();
    		}else{
    			submitStoreMsg();
    		}
		}else{
			Toast.makeText(context, "请完善重要信息！", Toast.LENGTH_LONG).show();
		}
	}
	
	
	/**
	 * 处理图片上传结果信息
	 * @param json string
	 * @return 返回图片是否上传成功
	 */
	private boolean onUpload(String json){
		boolean flag = false;
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			imageUrl = obj.getString("imageUrl");
			flag = true;
		}else{
			//dialog.cancel();
			Toast.makeText(context, "上传图片失败！", Toast.LENGTH_LONG).show();
		}
		return flag;
	}
	
	
	/**
	 * 处理更新店铺信息结果信息
	 * @param json string
	 */
	private void onComplete(String json){
		JsonUtils.parseAddOrUpdateResult(json, "更新", dialog, context);
		startActivity(new Intent(context,StoreManageActivity.class));
	}
	
	
	
	/**
	 * 检查填写信息
	 */
	private void checkMsg(){
		String temp = null;
		params.clear();
		canSubmit = true;
		params.add(new BasicNameValuePair("sId", Constants.STORE_ID));
		temp =title.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("title", temp));
		temp = name.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("name", temp));
		temp = phone.getText().toString();
		if(temp.equals("")){
			temp = "0";
		}
		params.add(new BasicNameValuePair("phone", temp));
		temp = qq.getText().toString();
		if(temp.equals("")){
			temp = "0";
		}
		params.add(new BasicNameValuePair("qq", temp));
		temp =wechat.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("wechat", temp));
		temp = address.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("address", temp));
		temp = notice.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("notice", temp));
		temp = email.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("email", temp));
	    params.add(new BasicNameValuePair("imageUrl",imageUrl));
	}
	/**
	 * 提交数据
	 */
	private void submitStoreMsg(){
		String url = RequestParam.UPDATE_STORE_PATH;                                                           //提交更新信息网址
		AsyncPost asyncHttpPost = new AsyncPost(url, params,handler,0);     //发起Post请求
		asyncHttpPost.execute();
	}
	
	
    /**
     * 获取初始化信息
     */
    private void getInitData(){
    	String url = RequestParam.GET_STORE_PATH;
    	params.clear();
    	params.add(new BasicNameValuePair("sId", Constants.STORE_ID));
    	//new AsyncHttpPost(url,params,handler,2).execute();
    	//dialog.show();
    }
    
    
    /**
     * 解析获取信息
     * @param json
     */
    private void onGetData(String json){
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			JSONObject data = obj.getJSONObject("data");
			setInitData(data);
		}else{
			Toast.makeText(context, "获取信息失败，请重试！", Toast.LENGTH_LONG).show();
		}
		//dialog.cancel();
    }
    
    
    /**
     * 设置初始化信息
     */
    private void setInitData(JSONObject object){
    	/*name.setText(object.getString("name"));
    	title.setText(object.getString("title"));
    	phone.setText(object.getString("phone"));
    	qq.setText(object.getString("qq"));
    	wechat.setText(object.getString("wechat"));
    	address.setText(object.getString("address"));
    	email.setText(object.getString("email"));
    	notice.setText(object.getString("notice"));
    	imageUrl = object.getString("imageUrl").replace("\\", "");
    	ImageLoader.getInstances().displayImage(imageUrl, image);*/
    	name.setText("吴牧");
    	title.setText("海澜之家");
    	phone.setText("18223022677");
    	qq.setText("741256953");
    	wechat.setText("wmwxdh");
    	address.setText("重庆市观音桥步行街9号附10号");
    	email.setText("741256953@qq.com");
    	notice.setText("海澜之家男装，高品位;多款式面向大众，男人的衣柜,打造\"一站式\"购物体验!");
    	image.setImageResource(R.drawable.header);
    }


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
