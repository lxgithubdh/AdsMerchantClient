package com.whut.presenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.interfaces.IBasePresenter;
import com.whut.interfaces.IBaseView;
import com.whut.util.AsyncHttpPost;

/**
 * 商品列表管理器
 * @author lx
 */
public class GoodsListPresenter implements IBasePresenter{

	
	private IBaseView view;
	
	
	public GoodsListPresenter(IBaseView v){
		this.view = v;
	}
	
	
	@Override
	public void response(String data,int respondCode) {
		view.setInfo(data,respondCode);
	}


	@Override
	public void request(int requestCode) {
		String url = RequestParam.GET_GOODS_LIST;
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("sId", Constants.STORE_ID));        //添加参数
		new AsyncHttpPost(this,url, list, requestCode).execute();
	}

}
