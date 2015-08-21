package com.whut.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.data.model.GoodsModel;
import com.whut.imageloader.ImageLoader;
import com.whut.seller.R;

/**
 * 商品列表适配器
 * @author lx
 */
public class GoodsListUtil {

	//商品列表
	private ArrayList<GoodsModel>  list = new ArrayList<GoodsModel>();
	//程序上下文
	private Context context;
	
	public GoodsListUtil(Context context,String data){
		this.context = context;
		this.list = parseData(data);
	}
	
	
	/**
	 * 获取商品列表信息适配器
	 * @return
	 */
	public BaseAdapter getAdapter(){
		return new GoodsListAdapter(context, list);
	}
	
	
	public ArrayList<GoodsModel> getGoodsList(){
		return list;
	}
	
	
	/**
	 * 解析Json数据，返回商品信息列表
	 */
	private ArrayList<GoodsModel> parseData(String data){
		ArrayList<GoodsModel> result = new ArrayList<GoodsModel>();
		if (JsonUtils.isGoodJson(data)) {
			JSONObject json = JSONObject.parseObject(data);
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
					
					result.add(goods);
				}
			} else {
				String msg = "获取信息失败，请重试！";
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
		}
		return result;
	}
}

class GoodsListAdapter extends BaseAdapter{

	
	private ArrayList<GoodsModel> list = null;
	private LayoutInflater inflater;
	
	public GoodsListAdapter(Context context ,ArrayList<GoodsModel> data){
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holder = null;
		if (convertView != null) {
			holder = (HolderView) convertView.getTag();
		}
		if(holder==null){
			holder = new HolderView();
			convertView = inflater.inflate(R.layout.goods_list_item,
					null);
			holder.desc = (TextView) convertView
					.findViewById(R.id.goods_list_desc);
			holder.img = (ImageView) convertView
					.findViewById(R.id.goods_list_image);
			holder.inventory = (TextView) convertView
					.findViewById(R.id.goods_list_inventory);
			holder.currentPrice=(TextView)convertView.findViewById(R.id.goods_list_current_price);
			holder.originalPrice=(TextView)convertView.findViewById(R.id.goods_list_original_price);
			holder.originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.sales=(TextView)convertView.findViewById(R.id.goods_list_sales);
			convertView.setTag(holder);
		}
		GoodsModel model = list.get(position);
		holder.desc.setText(model.getDesc());
		holder.currentPrice.setText(String.valueOf(model.getCurrentPrice()));
		holder.originalPrice.setText(String.valueOf(model.getOriginalPrice()));
		holder.sales.setText(String.valueOf(model.getSales()));
		holder.inventory.setText(String.valueOf(model.getInventory()));
		
		ImageLoader.getInstances().displayImage(
				list.get(position).getImageUrl(), holder.img);
		
		/*
		 * Bitmap bitmap = BitmapFactory.decodeFile(items .get(position
		 * - 3).getImg_url());
		 */
		// holder.img.setImageBitmap(bitmap);
		return convertView;
	}
	
	
	private class HolderView {
		TextView currentPrice;
		TextView originalPrice;
		TextView desc;
		ImageView img;
		TextView sales;
		TextView inventory;
	}
}
