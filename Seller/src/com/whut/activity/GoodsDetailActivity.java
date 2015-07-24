package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.pgyersdk.Pgy;
import com.whut.seller.R;
import com.whut.util.AsyncHttpPost;
import com.whut.util.AsyncUploadFile;
import com.whut.util.ImageUtil;
import com.whut.util.JsonUtils;
import com.whut.util.SelectImage;
import com.whut.util.SlipAction;
import com.whut.config.Constants;
import com.whut.data.model.GoodsModel;
import com.whut.imageloader.ImageLoader;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author lx
 *商品上架
 */
public class GoodsDetailActivity extends Activity {

	//商品图片
	private ImageView image;
	//选择图片
	private SelectImage selectImage;
	//商品图片链接
	private String imageUrl;
	//是否支持随时退款
	private String isReturn="true";
	//处理消息循环
	private Handler handler;
	//进度条
	private ProgressDialog dialog;
	//是否更换图片
	private boolean changeImage = false;
	//是否可以提交
	private boolean canSubmit = true;
	//商品类别
	private int goodsCategory = 0;
	private List<NameValuePair> params = new ArrayList<NameValuePair>() ;  //参数列表
	private Context context;
	
	private EditText desc;
	private EditText title;
	private Spinner category;
	private EditText originalPrice;
	private EditText currentPrice;
	private EditText inventory;
	private RadioButton canReturn;
	private EditText notice;
	private EditText buyDetail;
	private String gId;
	
	
	/**
	 * 初始化方法
	 */
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_goods_detail);
		
		context = this;
		Pgy.init(context, Constants.APP_ID);
        dialog = new ProgressDialog(context);
		image = (ImageView)findViewById(R.id.goods_info_image);
		selectImage = new SelectImage(context);
		RadioGroup group = (RadioGroup)findViewById(R.id.goods_info_is_return);
		desc = (EditText)findViewById(R.id.goods_info_desc);
		title = (EditText)findViewById(R.id.goods_info_title);
		category = (Spinner)findViewById(R.id.goods_info_category);
		originalPrice = (EditText)findViewById(R.id.goods_info_original_price);
		currentPrice = (EditText)findViewById(R.id.goods_info_current_price);
		inventory = (EditText)findViewById(R.id.goods_info_inventory);
		notice = (EditText)findViewById(R.id.goods_info_notice);
		buyDetail = (EditText)findViewById(R.id.goods_info_buy_detail);
		
		GoodsModel goods = (GoodsModel) getIntent().getExtras().get("goods");
		gId = goods.getGid();
		imageUrl = goods.getImageUrl();
		desc.setText(goods.getDesc());
		title.setText(goods.getTitle());
		category.setSelection(goods.getCatgory());
		originalPrice.setText(String.valueOf(goods.getOriginalPrice()));
		currentPrice.setText(String.valueOf(goods.getCurrentPrice()));
		inventory.setText(String.valueOf(goods.getInventory()));
		notice.setText(goods.getNotice());
		buyDetail.setText(goods.getBuyDetail());
		if(goods.isReturnAnytime()){
			canReturn = (RadioButton)findViewById(R.id.goods_info_can_return);
			canReturn.setChecked(true);
			isReturn = "true";
		}else{
			canReturn = (RadioButton)findViewById(R.id.goods_info_can_not_return);
			canReturn.setChecked(true);
			isReturn = "false";
		}
		ImageLoader.getInstances().displayImage(imageUrl, image);
		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.goods_info_can_return){
					isReturn = "true";
				}else{
					isReturn = "false";
				}
			}
		});
		 
		category.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				goodsCategory = position;
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
				case 0:                      //更新商品信息
					onComplete(result);
					break;
				case 1:                       //图片上传
					imageUrl = JsonUtils.parseUploadImage(result,dialog, context);
					if(imageUrl!=null){
						submitGoods();
					}
					break;
				default :
					break;
				}
			}
		};
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
    			submitGoods();
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
		temp = inventory.getText().toString();
		if(temp.equals("")){
			canSubmit = false;
		}
		params.add(new BasicNameValuePair("inventory", temp));
		temp =originalPrice.getText().toString();
		if(temp.equals("")){
			temp = "0.0";
		}
		params.add(new BasicNameValuePair("originalPrice", temp));
		temp = currentPrice.getText().toString();
		if(temp.equals("")){
			temp = "0.0";
		}
		params.add(new BasicNameValuePair("currentPrice", temp));
		temp = notice.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("notice", temp));
		temp = buyDetail.getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		params.add(new BasicNameValuePair("buyDetail", temp));
		params.add(new BasicNameValuePair("category", String.valueOf(goodsCategory)));
		params.add(new BasicNameValuePair("isReturnAnytime", isReturn));
	    params.add(new BasicNameValuePair("imageUrl",imageUrl));
	}
	/**
	 * 提交数据
	 */
	private void submitGoods(){
		AsyncHttpPost asyncHttpPost = new AsyncHttpPost(Constants.UPDATE_GOODS_PATH, params,handler,0);     //发起Post请求
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
		SlipAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
