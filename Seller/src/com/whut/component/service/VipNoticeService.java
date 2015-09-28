package com.whut.component.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.whut.config.Constants;

/**
 * 接收vip进店推送信息
 * @author lx
 */
public class VipNoticeService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){
		try{
			URI uri = new URI("ws://115.28.9.186:8899/store/service/201/node-tair-web/notify/vip");
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("cookie", Constants.USER_COOKIE);
			WebSocketWorker worker = new WebSocketWorker(this,uri, new Draft_17(),params);
			worker.connectBlocking();
			worker.send("hello");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}


/**
 * 自定义websocket客户端
 * @author lx
 */
class WebSocketWorker extends WebSocketClient{

	
	private Context context;
	
	
	public WebSocketWorker(Context context,URI serverUri, Draft draft,Map<String, String> params) {
		super(serverUri, draft,params,1000);
		for(String key : params.keySet()){
			System.out.println(params.get(key));
		}
		this.context = context;
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {}

	@Override
	public void onError(Exception arg0) {}

	@Override
	public void onMessage(String msg) {
		Log.i("msg", msg);
		++Constants.VIP_NUM;
		Intent intent = new Intent();
		intent.setAction(Constants.VIP_BROADCAST);
		intent.putExtra("msg", msg);
		context.sendBroadcast(intent);
	}

	@Override
	public void onOpen(ServerHandshake shake) {
		Iterator<String> iterator = shake.iterateHttpFields();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key+":"+shake.getFieldValue(key));
		}
	}
	
}
