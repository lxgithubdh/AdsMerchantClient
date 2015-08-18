package com.whut.util;

import java.util.ArrayList;

import com.whut.seller.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class DateAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> lstDate;
	private int windowHeight;
	private int windowWidth;
	private static final int[] icons = null;
		/*{ R.drawable.channal_business, R.drawable.wifi_manage,
			R.drawable.preferential_manage, R.drawable.real_store_manage, R.drawable.account_manage,
			R.drawable.comment, R.drawable.portal, R.drawable.guest_flow };*/

	@SuppressWarnings("deprecation")
	public DateAdapter(Activity mContext, ArrayList<String> list) {
		this.context = mContext;
		lstDate = list;
		windowHeight = mContext.getWindowManager().getDefaultDisplay()
				.getHeight();
		windowWidth = mContext.getWindowManager().getDefaultDisplay()
				.getWidth();

	}

	@Override
	public int getCount() {
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		lstDate.add(startPosition, (String) endObject);
		lstDate.remove(startPosition + 1);
		lstDate.add(endPosition, (String) startObject);
		lstDate.remove(endPosition + 1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.grid_item,null);
		convertView.setBackgroundResource(R.drawable.corners_bg);
		//convertView.setBackgroundColor(Color.WHITE);
		convertView.getBackground().setAlpha(50);
		//FrameLayout mlayout = new FrameLayout(context);
		//mlayout.setPadding(3, 3, 3, 3);
		//convertView.setPadding(3, 3, 3, 3);
		TextView tv = (TextView) convertView.findViewById(R.id.tv_grid);
		ImageView iv = (ImageView) convertView.findViewById(R.id.iv_grid);
		//ImageView iv = new ImageView(context);

		 //TextView tv = new TextView(context);
		/*
		 * if(lstDate.get(position)==null){ txtAge.setText("+");
		 * txtAge.setBackgroundResource(R.drawable.red); } else
		 * if(lstDate.get(position).equals("none")){ txtAge.setText("");
		 * txtAge.setBackgroundDrawable(null); }else txtAge.setText("Item" +
		 * lstDate.get(position));
		 */
		int width = (int) (windowWidth * 0.145);
		int height = (int) (windowHeight * 0.115);
		// System.out.println("width:" + width + "\nheight:" + height);
		iv.setLayoutParams(new LayoutParams(width, height));
		// 设置背景颜色
		// iv.setBackgroundResource(R.drawable.blue);
		// 设置透明
		//iv.setBackgroundColor(Color.WHITE);
		//iv.getBackground().setAlpha(50);
		iv.setImageResource(icons[position]);
		//mlayout.addView(iv);
		tv.setTextSize(16);
		tv.setText(lstDate.get(position));
		tv.setTextColor(Color.WHITE);
		//tv.setBackgroundColor(Color.WHITE);
		//tv.getBackground().setAlpha(50);
		//mlayout.addView(tv);
		// 添加右上角的更新图标

		//String[] MAIN_ITEM_IS_UPDATE = { "0", "0", "0", "0", "0", "0", "0", "0" };

		//if (!MAIN_ITEM_IS_UPDATE[position].equals("0")) {
			/*ImageView update = new ImageView(context);
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(50, 50);
			lp.gravity = Gravity.RIGHT;
			lp.rightMargin = 8;
			lp.topMargin = 5;
			update.setLayoutParams(lp);*/
			// update.setBackgroundColor(Color.RED);
			// update.setBackgroundResource(R.drawable.main_new);
			//mlayout.addView(update);
		//}

		return convertView;
		//return mlayout;
	}
}
