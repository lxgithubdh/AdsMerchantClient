package com.whut.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pgyersdk.Pgy;
import com.whut.config.Constants;
import com.whut.fragment.GoodsOffShelvesFragment;
import com.whut.fragment.GoodsOnShelvesFragment;
import com.whut.seller.R;
import com.whut.util.BackAction;

/**
 * 商品列表
 * @author lx
 */
public class GoodsListActivity extends Activity implements OnClickListener{
	
	
	//销售中
	private TextView onShelves;
	private GoodsOnShelvesFragment onShelvesFragment;
	//已下架
	private TextView offShelves;
	private GoodsOffShelvesFragment offShelvesFragment;
	//Fragment管理器
	private FragmentManager manager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_list);
		Pgy.init(GoodsListActivity.this, Constants.APP_ID);
		initView();
		setTabView(0);
	}

	
	/**
	 * 初始化
	 */
	private void initView(){
		onShelves = (TextView)findViewById(R.id.goods_on_shelves);
		offShelves = (TextView)findViewById(R.id.goods_off_shelves);
		onShelves.setOnClickListener(this);
		offShelves.setOnClickListener(this);
		manager = getFragmentManager();
	}
	
	
	/**
	 * 设置显示Fragment
	 * @param i
	 */
	private void setTabView(int i) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = manager.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			offShelves.setTextColor(getResources().getColor(R.color.main_color));
			offShelves.setBackground(getResources().getDrawable(R.drawable.tab_right_have_not_bg));
			onShelves.setTextColor(getResources().getColor(R.color.white));
			onShelves.setBackground(getResources().getDrawable(R.drawable.tab_left_have_bg));

			if (onShelvesFragment != null) {
				transaction.show(onShelvesFragment);
			} else {
				onShelvesFragment = new GoodsOnShelvesFragment();
				onShelvesFragment.setContext(this);
				transaction.add(R.id.goods_list_show, onShelvesFragment);
			}
			break;
		case 1:
			offShelves.setTextColor(getResources().getColor(R.color.white));
			offShelves.setBackground(getResources().getDrawable(R.drawable.tab_right_have_bg));
			onShelves.setTextColor(getResources().getColor(R.color.main_color));
			onShelves.setBackground(getResources().getDrawable(R.drawable.tab_left_have_not_bg));

			if (offShelvesFragment != null) {
				transaction.show(offShelvesFragment);
			} else {
				offShelvesFragment = new GoodsOffShelvesFragment();
				offShelvesFragment.setContext(this);
				transaction.add(R.id.goods_list_show,offShelvesFragment);
			}
			break;
		}
		transaction.commit();
	}

	
	/**
	 * 隐藏Fragment
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (onShelvesFragment != null)
			transaction.hide(onShelvesFragment);
		if (offShelvesFragment != null)
			transaction.hide(offShelvesFragment);
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
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goods_on_shelves:
			setTabView(0);
			break;
		case R.id.goods_off_shelves:
			setTabView(1);
			break;
		}
	}
}


