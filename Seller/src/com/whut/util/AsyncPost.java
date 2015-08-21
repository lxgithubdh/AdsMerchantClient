package com.whut.util;

import java.util.List;

import org.apache.http.NameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 发送Post请求
 * @author lx
 *
 */
public class AsyncPost extends AsyncTask<Void, Integer, String> {

	private String url;
	private List<NameValuePair> values;
	private Handler handler;
	//结果类型，0：更新、添加；1：上传；2：获取数据
	private int what;

	public AsyncPost(String url,List<NameValuePair> vlaues,Handler handler,int what){
		this.url = url;
		this.values = vlaues;
		this.handler = handler;
		this.what = what;
	}
	
	@Override
	protected String doInBackground(Void... params) {
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
		Bundle bundle = new Bundle();
		bundle.putString("res", res);
		Message msg = new Message();
		msg.setData(bundle);
		msg.what=what;
		handler.sendMessage(msg);
	}
	
}
