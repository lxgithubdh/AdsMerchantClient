package com.whut.presenter;

import java.util.List;

import org.apache.http.NameValuePair;

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
	public void saveData(Object data) {
		
	}

	@Override
	public void showData(Object data) {
		view.setInfo(data);
	}


	@Override
	public void request(String url, List<NameValuePair> pairs, String... params) {
		new AsyncHttpPost(url, pairs, this).execute();
	}

}
