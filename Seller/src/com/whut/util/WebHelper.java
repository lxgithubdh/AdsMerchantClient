package com.whut.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author Administrator 
 */
public class WebHelper {
	
	public static String getJsonString(String url) {
		// System.out.println(url);
		String result = "";
		String line = "";
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 10000);
			HttpResponse response = httpclient.execute(new HttpGet(url));
			is = response.getEntity().getContent();
			if (is != null) {
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(is));
				while ((line = rd.readLine()) != null) {
					result += line;
				}
				is.close();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// InputStream to Drawable
	@SuppressWarnings("deprecation")
	public static Drawable getDrawable(String url) {
		Drawable d = null;
		InputStream is = null;
		if (url != null && !url.equals("null"))
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(new HttpGet(url));
				is = response.getEntity().getContent();

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				// options.inSampleSize = 2; //
				// width��hight��Ϊԭ����4��һ���Է��ڴ�й¶
				Bitmap btp = BitmapFactory.decodeStream(is, null, options);
				d = new BitmapDrawable(btp);
				d.setCallback(null);
				is.close();
				return d;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	public static String getJsonString(String url, List<NameValuePair> list)
			throws Exception {
		HttpEntity entity = new UrlEncodedFormEntity(list);
		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		return showResponseResult(response);
	}

	private static String showResponseResult(HttpResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		if (response == null)
			return null;
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		StringBuilder res = new StringBuilder("");
		while (null != (line = reader.readLine())) {
			res.append(line);
		}
		System.out.println(res);

		return res.toString();
	}

}
