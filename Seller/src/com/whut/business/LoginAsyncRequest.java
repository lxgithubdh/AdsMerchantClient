package com.whut.business;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.whut.config.Constants;
import com.whut.interfaces.IBasePresenter;

import android.os.AsyncTask;

/**
 * 管理用户登录
 * @author lx
 */
public class LoginAsyncRequest extends AsyncTask<String, Void, String> {

	
	private IBasePresenter presenter;
	
	
	public LoginAsyncRequest(IBasePresenter presenter){
		this.presenter = presenter;
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("account", params[1]));
		list.add(new BasicNameValuePair("password", params[2]));
		StringBuilder res = new StringBuilder("");
		try{
			HttpEntity entity = new UrlEncodedFormEntity(list);
			HttpPost post = new HttpPost(params[0]);
			post.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			if (response == null)
				return null;
			Header[] headers = response.getHeaders("set-cookie");
			if(headers.length!=0){
				Constants.USER_COOKIE = headers[0].getValue();
			}else{
				throw new Exception();
			}
			HttpEntity respondEntity = response.getEntity();
			InputStream is = respondEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while (null != (line = reader.readLine())) {
				res.append(line);
			}
		}catch(Exception e){
			return "{\"code\":0,\"msg\":\""+e.toString()+"\"}";
		}
		return res.toString();
	}


	@Override
	protected void onPostExecute(String result) {
		presenter.response(result, 0);
		super.onPostExecute(result);
	}
}
