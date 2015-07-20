package com.whut.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;


/**
 * ImageView辅助类
 * @author lx
 */
public class ImageUtil {

	
	/**
	 * 获取ImageView内部bitmap对象
	 * @param ImageView
	 * @return bitmap对象
	 */
	public static Bitmap getBitmap(ImageView view){
		Bitmap bitmap = null;
		view.setDrawingCacheEnabled(true);
		bitmap = view.getDrawingCache().copy(Config.ARGB_8888, false);
		view.setDrawingCacheEnabled(false);
		return bitmap;
	}
}
