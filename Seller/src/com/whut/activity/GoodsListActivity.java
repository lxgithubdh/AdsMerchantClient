package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pgyersdk.Pgy;
import com.pgyersdk.crash.PgyCrashManager;
import com.whut.config.Constants;
import com.whut.data.model.GoodsModel;
import com.whut.imageloader.ImageLoader;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.SlipAction;
import com.whut.util.WebHelper;

/**
 * 商品列表
 * @author lx
 */
public class GoodsListActivity extends Activity{
	
	//上下文
	private Context context;
	//列表
	private ListView listview;
	//存储商品列表信息
	private List<GoodsModel> items;

	private MainListAdapter listAdapter;
	private LayoutInflater inflater;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_list);
		/*
		 * StrictMode.ThreadPolicy policy = new
		 * StrictMode.ThreadPolicy.Builder() .permitAll().build();
		 * StrictMode.setThreadPolicy(policy);
		 */
		Pgy.init(GoodsListActivity.this, Constants.APP_ID);
		initData();
		listview=(ListView) findViewById(R.id.common_list_view);
		listAdapter = new MainListAdapter();
		listview.setAdapter(listAdapter);
		enterStoreDetail();            
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		new GetGoodsList().execute(Constants.STORE_ID);
	}

	
	/**
	 * 返回
	 */
	public void onBack(View v){
		this.finish();
	}

	
	/**
	 * 添加商品
	 */
	public void addGoods(View v){
		Intent intent = new Intent(GoodsListActivity.this,GoodsShelvesActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 注册监听器，点击跳转
	 */
	private void enterStoreDetail() {
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, GoodsDetailActivity.class);
				intent.putExtra("goods", items.get(position));        //将全部信息传至详情页
				startActivity(intent);
			}
		});
	}

	
	/**
	 * 初始化
	 */
	private void initData() {
		context = this;
		items = new ArrayList<GoodsModel>();
	}

	
	@Override
	protected void onRestart() {
		super.onRestart();
	}


	
	/**
	 * 获取商品列表
	 * @author lx
	 *param 商铺Id sId
	 */
	class GetGoodsList extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String result = "";
			String url = Constants.GET_GOODS_LIST;
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("sId", params[0]));        //添加参数
			// http://202.114.175.253:80/ECheckServer/login/load.do?username=zym&password=12345
			try {
				result = WebHelper.getJsonString(url, list);
			} catch (Exception e) {
				result = "{\"code\":0,\"msg\":\""+e.toString()+"\",\"data\":[]}";
				PgyCrashManager.reportCaughtException(context, e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (JsonUtils.isGoodJson(result)) {
				JSONObject json = JSONObject.parseObject(result);
				int code = json.getIntValue("code");
				//String msg = json.getString("msg");
				if (code == 1) {
					JSONArray jsons = json.getJSONArray("data");
					GoodsModel goods;
					for (int i = 0; i < jsons.size(); i++) {
						goods = new GoodsModel();
						JSONObject item = jsons.getJSONObject(i);
						String title = item.getString("title");
						String desc = item.getString("desc");
						String img_url = item.getString("thumbnailUrl").replace("\\", "");
						String gid = item.getString("gId");
						/*
						 * String filepath = new DownloadURLFile()
						 * .downloadFromUrl(img_url, path);
						 * System.out.println(path + "------" + filepath);
						 */
						goods.setGid(gid);
						goods.setDesc(desc);
						goods.setImageUrl(img_url);
						goods.setTitle(title);
						try{
							goods.setOriginalPrice(item.getDoubleValue("originalPrice"));
						}catch(Exception e){
							goods.setOriginalPrice(0);
						}
						try{
							goods.setCurrentPrice(item.getDoubleValue("currentPrice"));
						}catch(Exception e){
							goods.setCurrentPrice(0);
						}
						try{
							goods.setReturnAnytime(item.getBooleanValue("isReturnAnytime"));
						}catch(Exception e){
							goods.setReturnAnytime(false);
						}
						try{
							goods.setInventory(item.getIntValue("inventory"));
						}catch(Exception e){
							goods.setInventory(0);
						}
						goods.setCatgory(item.getIntValue("catagory"));
						goods.setNotice(item.getString("notice"));
						goods.setBuyDetail(item.getString("buyDetail"));
						
						items.add(goods);
						listAdapter.notifyDataSetChanged();
					}
				} else {
					String msg = "获取信息失败，请重试！";
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class MainListAdapter extends BaseAdapter {
		private class HolderView {
			TextView title;
			TextView desc;
			ImageView img;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			    HolderView holder;
				if (convertView == null) {
					holder = new HolderView();
					convertView = inflater.inflate(R.layout.goods_list_item,
							null);
					holder.desc = (TextView) convertView
							.findViewById(R.id.goods_list_desc);
					holder.img = (ImageView) convertView
							.findViewById(R.id.goods_list_image);
					holder.title = (TextView) convertView
							.findViewById(R.id.goods_list_inventory);
					convertView.setTag(holder);
				} else {
					holder = (HolderView) convertView.getTag();
					if (holder == null) {
						holder = new HolderView();
						convertView = inflater.inflate(R.layout.goods_list_item,
								null);
						holder.desc = (TextView) convertView
								.findViewById(R.id.goods_list_desc);
						holder.img = (ImageView) convertView
								.findViewById(R.id.goods_list_image);
						holder.title = (TextView) convertView
								.findViewById(R.id.goods_list_inventory);
						convertView.setTag(holder);
					}
				}
				String desc = items.get(position).getDesc();
				holder.desc.setText("      "+desc);
				String tempTitle = "库存："+String.valueOf(items.get(position).getInventory());
				holder.title.setText(tempTitle);
				
				ImageLoader.getInstances().displayImage(
						items.get(position).getImageUrl(), holder.img);
				
				/*
				 * Bitmap bitmap = BitmapFactory.decodeFile(items .get(position
				 * - 3).getImg_url());
				 */
				// holder.img.setImageBitmap(bitmap);
				return convertView;
		}
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		SlipAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}


