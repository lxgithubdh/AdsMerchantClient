package com.whut.activity;

import com.whut.interfaces.IBaseView;
import com.whut.util.BackAction;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

/**
 * 店铺列表
 * @author lx
 */
public class StoreListActivity extends Activity implements IBaseView{

	
	@Override
	public Object getInfo(int code) {
		return null;
	}

	
	@Override
	public void setInfo(Object obj, int code) {
		
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}
	
	
	/**
	 * 返回
	 * @param v
	 */
	public void onBack(View v){
		this.finish();
	}
}
