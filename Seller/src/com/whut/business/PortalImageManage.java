package com.whut.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.whut.activity.PortalImageActivity;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.imageloader.ImageLoader;
import com.whut.seller.R;
import com.whut.util.AsyncPost;
import com.whut.util.AsyncUploadFile;
import com.whut.util.ImageUtil;
import com.whut.util.JsonUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * 管理图片操作
 * @author lx
 */
public class PortalImageManage {

	//程序运行上下文
	private Context context;
	//显示图片ImageView
	private ImageView image;
	//参数列表
	private List<NameValuePair> param = new ArrayList<NameValuePair>();
	//进度框
	private ProgressDialog dialog;
	
	
	public PortalImageManage(ImageView imageView,Context context){
		this.image = imageView;
		this.context = context;
		dialog = new ProgressDialog(context);
	}
	
	/**
	 * 初始化ImageView
	 * @return true 初始化成功,false 获取图片失败
	 */
	public void initImageView(){
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				String res = msg.getData().getString("res");
				parseGetImage(res);
			}
		};
		String url = RequestParam.GET_PORTAL_IMAGE;
    	param.clear();                                                                                             //清空参数列表
    	param.add(new BasicNameValuePair("sId", Constants.STORE_ID));//添加参数
    	//new AsyncHttpPost(url,param,handler,2).execute();                             //发送请求
		//dialog.show();
		//dialog.setCancelable(false);
    	image.setImageResource(R.drawable.portal_image);
	}
	
	
	/**
	 * 上传并保存修改
	 * @return true 保存成功,false 保存失败
	 */
	public void saveChange(){
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				String result = msg.getData().getString("res");
				switch(msg.what){
				case 0:                      //更新信息
					parseUpdateMsg(result);
					break;
				case 1:                       //图片上传
					String url = JsonUtils.parseUploadImage(result, dialog, context);
					if(url!=null){
						param.clear();
						param.add(new BasicNameValuePair("imageUrl", url));
						updatePortalImage(this);
					}
					break;
				default :
					break;
				}
			}
		};
		Bitmap bitmap = ImageUtil.getBitmap(image);
		new AsyncUploadFile(bitmap,handler).execute();
		//dialog.show();
	}
	
	
	/**
	 * 设置ImageView显示内容
	 * @param bitmap
	 */
	public void setImage(Bitmap bitmap){
		image.setImageBitmap(bitmap);
	}

	
	
	/**
	 * 更新上传门户页面
	 */
	private void updatePortalImage(Handler handler){
		String url = RequestParam.UPDATE_PORTAL_IMAGE;                           //提交更新信息网址
		new AsyncPost(url, param,handler,0).execute();                              //发送post请求
	}
	
	
	/**
	 * 解析并处理获取图片返回信息
	 * @param json 返回结果Json字符串
	 * @return 处理结果 true 处理成功,false 处理失败
	 */
	private void parseGetImage(String json){
		//dialog.cancel();
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			String imageUrl = obj.getString("imageUrl");
			ImageLoader.getInstances().displayImage(imageUrl, image);
		}else{
			Toast.makeText(context, "获取图片失败!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	
	/**
	 * 解析更新返回结果信息
	 * @param json 更新返回结果Json字符串
	 * @return 结果信息 true 更新成功，false 更新失败
	 */
	private void parseUpdateMsg(String json){
		boolean flag = false;
		//dialog.cancel();
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			flag = true;
		}
		if(flag){
			Toast.makeText(context, "修改成功!", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(context,PortalImageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(intent);
		}else{
			Toast.makeText(context, "修改失败!", Toast.LENGTH_SHORT).show();
		}
	}
}
