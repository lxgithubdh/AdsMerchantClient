package com.whut.activity;

import com.pgyersdk.Pgy;
import com.whut.presenter.GoodsShelvesPresenter;
import com.whut.seller.R;
import com.whut.util.ImageUtil;
import com.whut.util.JsonUtils;
import com.whut.util.SelectImage;
import com.whut.util.BackAction;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.GoodsModel;
import com.whut.interfaces.IBaseView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author lx
 *商品上架
 */
public class GoodsShelvesActivity extends Activity implements IBaseView{

	//商品图片
	private ImageView image;
	//商品图片链接
	private String imageUrl;
	//是否支持随时退款
	private boolean isReturn=true;
	//进度条
	private ProgressDialog dialog;
	//是否可以提交
	private boolean canSubmit = true;
	//选择图片
	private SelectImage selectImage;
	//商品类别
	private int goodsCategory = 0;
	//商品类别列表
	private Spinner category;
	//商品添加管理器
	private GoodsShelvesPresenter presenter;
	//商品信息
	private GoodsModel model;
	private Context context;
	
	
	/**
	 * 初始化方法
	 */
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_goods_shelves);
		
		((TextView)findViewById(R.id.activity_title_complete)).setText("商品上架");
		context = this;
		Pgy.init(context, Constants.APP_ID);
		presenter = new GoodsShelvesPresenter(this);
		image = (ImageView)findViewById(R.id.goods_info_image);
		category = (Spinner)findViewById(R.id.goods_info_category);
        dialog = new ProgressDialog(context);
        selectImage = new SelectImage(context);
		RadioGroup group = (RadioGroup)findViewById(R.id.goods_info_is_return);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.goods_info_can_return){
					isReturn = true;
				}else{
					isReturn = false;
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
			public void onNothingSelected(AdapterView<?> parent) {}
		});
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
        model = checkMsg();
        if(canSubmit){
        	dialog.show();
            dialog.setCancelable(false);
            presenter.request(RequestParam.REQUEST_UPLOAD_IMAGE);
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
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	
	/**
	 * 检查输入信息
	 */
	private GoodsModel checkMsg(){
		String temp = null;
		canSubmit = true;
		GoodsModel model = new GoodsModel();
		temp =((EditText)this.findViewById(R.id.goods_info_title)).getText().toString();
		if(temp.equals("")){
			canSubmit = false;
		}
		model.setTitle(temp);
		temp = ((EditText)this.findViewById(R.id.goods_info_desc)).getText().toString();
		if(temp.equals("")){
			canSubmit = false;
		}
		model.setDesc(temp);
		temp = ((EditText)this.findViewById(R.id.goods_info_inventory)).getText().toString();
		if(temp.equals("")){
			canSubmit = false;
		}
		model.setInventory(Integer.valueOf(temp));
		temp =( (EditText)this.findViewById(R.id.goods_info_original_price)).getText().toString();
		if(temp.equals("")){
			temp = "0.0";
		}
		model.setOriginalPrice(Double.valueOf(temp));
		temp = ((EditText)this.findViewById(R.id.goods_info_current_price)).getText().toString();
		if(temp.equals("")){
			temp = "0.0";
		}
		model.setCurrentPrice(Double.valueOf(temp));
		temp = ((EditText)this.findViewById(R.id.goods_info_notice)).getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		model.setNotice(temp);
		temp = ((EditText)this.findViewById(R.id.goods_info_buy_detail)).getText().toString();
		if(temp.equals("")){
			temp = "无";
		}
		model.setBuyDetail(temp);
		model.setCatgory(goodsCategory);
		model.setReturnAnytime(isReturn);
		return model;
	}
	
	
	/**
	 * 提交数据
	 */
	private void submitGoods(){
		model.setImageUrl(imageUrl);
		presenter.request(RequestParam.REQUEST_ADD);
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


	@Override
	public Object getInfo(int code) {
		if(code==RequestParam.REQUEST_UPLOAD_IMAGE){
			return ImageUtil.getBitmap(image);
		}else{
			return model;
		}
	}


	@Override
	public void setInfo(Object obj,int code) {
		switch(code){
		case  RequestParam.REQUEST_UPLOAD_IMAGE:
			imageUrl = JsonUtils.parseUploadImage((String)obj, dialog, context);
			if(imageUrl!=null){
				submitGoods();
			}
			break;
		case RequestParam.REQUEST_ADD:
			onComplete((String)obj);
			break;
		default:
			break;
		}
	}
}
