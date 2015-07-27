package com.whut.activity;

import com.whut.seller.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ViewFlipper;


/**
 * 引导页界面
 * @author lx
 */
public class PrologueActivity extends Activity {

	
	//ViewFlipper实例，控制显示和切换
	private ViewFlipper flipper;
	//其实位置x坐标
	private float startX;
	//引导页数量
	private int pageNum = 4;
	//当前页码
	private int currentNum = 0;
	
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_prologue);
		flipper = (ViewFlipper)findViewById(R.id.prologue_flipper);
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			if(event.getX()<startX){                     //左划
				nextPage();
			}else{
				previousPage();                 //右划或点击                     
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 下一页
	 */
	private void nextPage(){
		currentNum++;
		if(currentNum<pageNum){
			flipper.showNext();
		}else{
			Intent intent = new Intent(PrologueActivity.this,LoginActivity.class);
			startActivity(intent);
			PrologueActivity.this.finish();
		}
	}
	
	/**
	 * 上一页
	 */
	private void previousPage(){
		if(currentNum>0){
			currentNum--;
			flipper.showPrevious();
		}
	}
}
