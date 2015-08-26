package com.whut.util;

import java.util.List;

import org.apache.http.NameValuePair;

import com.whut.interfaces.IBasePresenter;

import android.os.AsyncTask;

/**
 * 异步网络请求
 * @author lx
 */
public class AsyncHttpPost extends AsyncTask<String, Void, String> {

	private String url;
	private List<NameValuePair> values;
	private IBasePresenter presenter;
	private int requestCode;

	public AsyncHttpPost(IBasePresenter presenter,String url,List<NameValuePair> vlaues,int requestCode){
		this.url = url;
		this.values = vlaues;
		this.presenter = presenter;
		this.requestCode = requestCode;
	}
	
	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			result = WebHelper.getJsonString(url, values);
		} catch (Exception e) {
			result = "{\"code\":0,\"msg\":\""+e.toString()+"\"}";
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(String res){
		super.onPostExecute(res);
		presenter.response(res,requestCode);
	}

}
