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

	public AsyncHttpPost(String url,List<NameValuePair> vlaues,IBasePresenter presenter){
		this.url = url;
		this.values = vlaues;
		this.presenter = presenter;
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
		presenter.showData(res);
	}

}
