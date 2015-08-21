package com.whut.activity;

import java.util.ArrayList;

import com.whut.data.model.VipDetailModel;
import com.whut.data.model.VipRecordModel;
import com.whut.imageloader.ImageLoader;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.VipListPresenter;
import com.whut.seller.R;
import com.whut.util.BackAction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * vip进店记录界面
 * @author lx
 */
public class VipRecordActivity extends Activity implements IBaseView{

	//显示vip进店记录列表
	private LinearLayout layout;
	//vip进店管理控制器
	private VipListPresenter presenter;
	//布局管理
	private LayoutInflater inflater;
	
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_vip_record);
		
		layout = (LinearLayout)findViewById(R.id.vip_list);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		presenter = new VipListPresenter(this);
		presenter.setInfo();
	}
	
	@Override
	public Object getInfo() {
		return null;
	}

	@Override
	public void setInfo(Object obj) {
		ArrayList<VipRecordModel> records = (ArrayList<VipRecordModel>)obj;
		for(int i=0;i<records.size();++i){
			layout.addView(getView(records.get(i)));
		}
	}

	
	/**
	 * 获取每天vip进店记录视图
	 * @param model
	 */
	private View getView(VipRecordModel model){
		View view = inflater.inflate(R.layout.vip_list_item_date, null);
		TextView enterDate = (TextView)view.findViewById(R.id.vip_enter_date);
		ListView vipListDate = (ListView)view.findViewById(R.id.vip_list_date);
		enterDate.setText(model.getRecordDate());
		VipListAdapter adapter = new VipListAdapter(inflater, model.getVipList());
		vipListDate.setAdapter(adapter);
		setListViewHeight(vipListDate);
		return view;
	}
	
	
	/**
	 * 调整listview的高度
	 * @param view
	 */
	private void setListViewHeight(ListView view){
		ListAdapter listAdapter = view.getAdapter();
		if(listAdapter == null) {  
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, view);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.height = totalHeight + (view.getDividerHeight() * (listAdapter.getCount()-1));
		view.setLayoutParams(params);
	}
	
	
	/**
	 * 返回
	 */
	public void onBack(View view){
		this.finish();
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}



/**
 * vip每日进店记录
 * @author lx
 */
class VipListAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<VipDetailModel> models;
	
	
	public VipListAdapter(LayoutInflater inflater,ArrayList<VipDetailModel> models){
		this.inflater = inflater;
		this.models = models;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return models.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return models.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view != null) {
			holder = (ViewHolder) view.getTag();
		} 
		holder = new ViewHolder();
		view = inflater.inflate(R.layout.vip_list_item,
				null);
		holder.vipUserName = (TextView) view
				.findViewById(R.id.vip_user_name);
		holder.vipPortrait = (ImageView)view.findViewById(R.id.vip_portrait);
		holder.vipTag = (TextView)view.findViewById(R.id.vip_tag);
		holder.vipEnterTime = (TextView)view.findViewById(R.id.vip_enter_time);
		view.setTag(holder);
		VipDetailModel model = models.get(position);
		holder.vipUserName.setText(model.getVipUserName());
		holder.vipTag.setText(model.getVipTag());
		holder.vipEnterTime.setText(model.getEnterTime());
		ImageLoader.getInstances().displayImage(model.getPortraitImageUrl(), holder.vipPortrait);
		
		return view;
	}
	
	
	private class ViewHolder{
		ImageView vipPortrait;
		TextView vipUserName;
		TextView vipTag;
		TextView vipEnterTime;
	}
}
