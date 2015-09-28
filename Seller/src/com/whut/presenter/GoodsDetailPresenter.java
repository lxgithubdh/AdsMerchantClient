package com.whut.presenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Bitmap;

import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.GoodsModel;
import com.whut.interfaces.IBasePresenter;
import com.whut.interfaces.IBaseView;
import com.whut.util.AsyncHttpPost;
import com.whut.util.AsyncUploadImage;

public class GoodsDetailPresenter implements IBasePresenter {

	
	private IBaseView view;
	
	
	public GoodsDetailPresenter(IBaseView view){
		this.view = view;
	}
	
	@Override
	public void request(int requestCode) {
		switch(requestCode){
		case RequestParam.REQUEST_UPLOAD_IMAGE:
			Bitmap bitmap = (Bitmap)view.getInfo(RequestParam.REQUEST_UPLOAD_IMAGE);
			AsyncUploadImage uploadImage= new AsyncUploadImage(this,bitmap,RequestParam.REQUEST_UPLOAD_IMAGE);
    		uploadImage.execute();
			break;
		case RequestParam.REQUEST_UPDATE:
			GoodsModel model = (GoodsModel)view.getInfo(RequestParam.REQUEST_UPDATE);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("sId", Constants.STORE_ID));
			params.add(new BasicNameValuePair("title", model.getTitle()));
			params.add(new BasicNameValuePair("desc", model.getDesc()));
			params.add(new BasicNameValuePair("inventory", String.valueOf(model.getInventory())));
			params.add(new BasicNameValuePair("originalPrice", String.valueOf(model.getOriginalPrice())));
			params.add(new BasicNameValuePair("currentPrice", String.valueOf(model.getCurrentPrice())));
			params.add(new BasicNameValuePair("notice", model.getNotice()));
			params.add(new BasicNameValuePair("buyDetail", model.getBuyDetail()));
			params.add(new BasicNameValuePair("category", String.valueOf(model.getCatgory())));
			params.add(new BasicNameValuePair("isReturnAnytime", String.valueOf(model.isReturnAnytime())));
		    params.add(new BasicNameValuePair("imageUrl",model.getImageUrl()));
			AsyncHttpPost asyncHttpPost = new AsyncHttpPost(this, 
					RequestParam.UPDATE_GOODS_PATH, params, RequestParam.REQUEST_UPDATE);     //发起Post请求
			asyncHttpPost.execute();
			break;
		default :
			break;
		}
		
	}

	@Override
	public void response(String data, int respondCode) {
		switch(respondCode){
		case RequestParam.REQUEST_UPLOAD_IMAGE:
		case RequestParam.REQUEST_UPDATE:
			view.setInfo(data, respondCode);
			break;
		default:
			break;
		}
	}
}
