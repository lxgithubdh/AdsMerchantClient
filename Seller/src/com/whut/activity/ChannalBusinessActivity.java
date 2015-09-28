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
 * 渠道业务
 * @author lx
 */
public class ChannalBusinessActivity extends Activity {
	private List<String> list = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	private ChanelBusinessAdapter adapter;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_business);
		
		((TextView)findViewById(R.id.activity_title_add)).setText("渠道业务管理");
		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.Main_ListView);
		adapter = new ChanelBusinessAdapter();
		listview.setAdapter(adapter);
		enterDetail();
	}

	private void enterDetail() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context,
						ChannalBusinessDetailActivity.class));
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		context = this;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = new ArrayList<String>();
		list.add("海澜之家");
	}

	
	/**
	 * 渠道业务适配器
	 * @author lx
	 */
	class ChanelBusinessAdapter extends BaseAdapter {

		class HolderView {
			TextView text;
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
						R.layout.channal_business_listitem, null);
				holder.text = (TextView) convertView
						.findViewById(R.id.channal_business_tv);
				convertView.setTag(holder);
			} else
				holder = (HolderView) convertView.getTag();
			holder.text.setText(list.get(position));
			holder.text.setTextSize(18);
			holder.text.setPadding(0, 5, 0, 5);
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
