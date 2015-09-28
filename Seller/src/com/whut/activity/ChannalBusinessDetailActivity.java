package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import com.whut.seller.R;
import com.whut.util.BackAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 渠道业务详情
 * @author lx
 */
public class ChannalBusinessDetailActivity extends Activity {
	private Context context;
	private ListView listview;
	private List<String> list;
	private LayoutInflater inflater;
	private ChannelBusinessDetailAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_business_detail);
		((TextView)findViewById(R.id.activity_title)).setText("普天管理");
		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.Main_ListView);
		adapter = new ChannelBusinessDetailAdapter();
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = null;
				switch(position){
				case 0:
					intent = new Intent(ChannalBusinessDetailActivity.this,GoodsListActivity.class);
				    startActivity(intent);
					break;
				case 1:
					intent = new Intent(ChannalBusinessDetailActivity.this,WebPageActivity.class);
					startActivity(intent);
					break;
			    default:
					break;
				}
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		context = this;
		list = new ArrayList<String>();
		list.add("信息管理");
		list.add("展现管理");
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	
	/**
	 * 列表适配器
	 * @author lx
	 */
	class ChannelBusinessDetailAdapter extends BaseAdapter {
		class HolderView {
			TextView tv;
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
			// TODO Auto-generated method stub
			HolderView holder;
			if (convertView == null) {
				holder = new HolderView();
				convertView = inflater.inflate(
						R.layout.channal_business_detail_listitem, null);
				holder.tv = (TextView) convertView
						.findViewById(R.id.channal_business_detail_item_title);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			holder.tv.setText(list.get(position));
			holder.tv.setTextSize(18);
			return convertView;
		}

	}

	/**
	 * 返回
	 * @param v
	 */
	public void onBack(View v) {
		this.finish();
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
}
