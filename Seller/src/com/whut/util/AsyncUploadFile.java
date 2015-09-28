package com.whut.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.whut.config.RequestParam;

/**
 * 异步上传图片
 * @author lx
 *
 */
public class AsyncUploadFile extends AsyncTask<Void, Integer, String>{

	private Bitmap bitmap;
	private Handler handler;
	
	public AsyncUploadFile(Bitmap bitmap,Handler handler){
		this.bitmap = bitmap;
		this.handler = handler;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		return uploadFile(bitmap);
	}
	
	@Override
	protected void onPostExecute(String res){
		Bundle bundle = new Bundle();
		bundle.putString("res", res);
		Message msg = new Message();
		msg.what = 1;
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	


/**
 * 质量压缩bitmap
 * @param image
 * @return 压缩后的bitmap
 */
private byte[] compressBitmap(Bitmap image) {  
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
    return baos.toByteArray();  
}  


/**
 * 上传图片
 * @param image
 * @return 结果信息
 */
private String uploadFile(Bitmap image){
	String end = "\r\n";
	String tokens = "--";
	String boundary = "**********147mnb";
	String result = null;
	HttpURLConnection con = null;
	try{
		URL url = new URL(RequestParam.UPLOAD_PATH_IMAGE);
		con = (HttpURLConnection)url.openConnection();                           //建立连接
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);     //设置请求头
		DataOutputStream dos = new DataOutputStream(con.getOutputStream());                //打开连接
		dos.writeBytes(tokens+boundary+end);
		dos.writeBytes("Content-Disposition:form-data;name=\"file-input-multiple\"; "
				+ "filename=\""+String.valueOf(new Date().getTime())+".png\""+end);
		dos.writeBytes("Content-Type:image/jpeg"+end+end);
		dos.write(compressBitmap(image));
		/*FileInputStream fis = new FileInputStream(imagePath);                            //添加文件
		byte[] data = new byte[1024];
		int l = -1;
		while((l=fis.read(data))!=-1){
			dos.write(data,0,l);
		}
		fis.close();*/
		dos.writeBytes(end);
		dos.writeBytes(tokens+boundary+tokens+end);
		dos.flush();
		InputStream is = con.getInputStream();                        //打开输入流               
		int ch;
		StringBuffer buffer = new StringBuffer();
		while((ch=is.read())!=-1){
			buffer.append((char)ch);
		}
		is.close();
		result = buffer.toString();
		dos.close();
	}catch(Exception e){
		result = "{\"code\":0,\"imageUrl\":\"\",\"msg\":\""+e.toString()+"\"}";
	}finally{
		if(con!=null){
			con.disconnect();
		}
	}
	return result;
}

}