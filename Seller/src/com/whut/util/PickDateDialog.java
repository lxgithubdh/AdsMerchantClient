package com.whut.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;


/**
 * 选择日期对话框
 * @author lx
 */
public class PickDateDialog implements OnFocusChangeListener{

	
	//上下文
	private Context context;
	//显示日期
	private EditText date;
	
	
	public PickDateDialog(Context context,EditText text){
		this.context = context;
		this.date = text;
	}
	

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			selectDate();
		}
	}
	
	
	/**
	 * 弹出获取日期对话框
	 */
	private void selectDate(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dialog = new DatePickerDialog(context, 
				new DateSetListener(date), year, month, day);
		dialog.show();
	}
}


/**
 * 选择日期处理函数
 * @author lx
 */
class DateSetListener implements OnDateSetListener{
	
	
	private EditText text;
	
	public DateSetListener(EditText text){
		this.text = text;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		    String res = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1)+"-"+
				String.valueOf(dayOfMonth);
			text.setText(res);
	}
	
}
