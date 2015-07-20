package com.whut.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.seller.R;
import com.whut.util.Configure;
import com.whut.util.DateAdapter;
import com.whut.util.DragGrid;
import com.whut.util.ScrollLayout;

public class MainActivity extends Activity {
	/** GridView. */
	private Context context;
	private LinearLayout linear;
	private RelativeLayout relate;
	private DragGrid gridView;
	private ScrollLayout lst_views;
	private TextView tv_page;// int oldPage=1;
	private ImageView runImage, delImage;
	LinearLayout.LayoutParams param;

	TranslateAnimation left, right;
	Animation up, down;

	private DateAdapter adapter;
	private boolean okToExit;

	private SharedPreferences mSharedPreferences;

	private static final String[] names = { "我的店铺", "WiFi管理", "优惠管理", "店铺管理",
			"账号管理", "查看评论", "门户设置", "客流情况" };

	public static final int PAGE_SIZE = 8;
	ArrayList<DragGrid> gridviews = new ArrayList<DragGrid>();

	ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
	ArrayList<String> lstDate = new ArrayList<String>();// 每一页的数据

	SensorManager sm;
	SensorEventListener lsn;
	boolean isClean = false;
	Vibrator vibrator;
	int rockCount = 0;

	/**
	 * 启动各个模块
	 * 
	 * @param index
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		okToExit = false;
		
		
		// 更新软件的异步类, 更新软件可以打开
		// SoftwareUpdate.newInstance(this).execute();
		// 更新首页图片
		// SplashUpdate.newInstance(this).execute();
		for (int i = 0; i < 8; i++) {
			lstDate.add("" + names[i]);
		}

		init();
		initData();

		for (int i = 0; i < Configure.countPages; i++) {
			lst_views.addView(addGridView(i));
		}

		lst_views.setPageListener(new ScrollLayout.PageListener() {
			@Override
			public void page(int page) {
				setCurPage(page);
			}
		});

		runImage = (ImageView) findViewById(R.id.run_image);
		// runAnimation();
		delImage = (ImageView) findViewById(R.id.dels);// 下方删除
		relate.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				// System.out.println("LongClick");
				return false;
			}
		});
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {
				if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					if (!isClean && rockCount >= 10) {
						isClean = true;
						rockCount = 0;
						vibrator.vibrate(100);
						CleanItems();
						return;
					}
					@SuppressWarnings("deprecation")
					float newX = e.values[SensorManager.DATA_X];
					@SuppressWarnings("deprecation")
					float newY = e.values[SensorManager.DATA_Y];
					@SuppressWarnings("deprecation")
					float newZ = e.values[SensorManager.DATA_Z];
					// if ((newX >= 18 || newY >= 20||newZ >= 20 )&&rockCount<4)
					// {
					if ((newX >= 18 || newY >= 20 || newZ >= 20)
							&& rockCount % 2 == 0) {
						rockCount++;
						return;
					}
					if ((newX <= -18 || newY <= -20 || newZ <= -20)
							&& rockCount % 2 == 1) {
						rockCount++;
						return;
					}

				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}
		};

		sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
	}

	public void settings(View v) {
		// 启动设置activity
		
		 Intent  intent = new Intent(context, SettingActivity.class);
		 startActivity(intent);
		 
	}

	private void startActivity(int index) {
		// TODO Auto-generated method stub
		// System.out.println(index);
		// 获得打开的时间,并去掉提醒更新图标
		//getOpenTime(index);
		Intent intent;
		switch (index) {
		case 0: // 我的店铺
			intent = new Intent(context, ChannalBusinessActivity.class);
			startActivity(intent);
			break;
		case 1: // WiFi管理
			intent = new Intent(context, WiFiManageActivity.class);
			startActivity(intent);
			break;
		case 2: // 优惠管理
			intent = new Intent(context, PreferentialManagerActivity.class);
			startActivity(intent);
			break;
		case 3: // 店铺管理
			intent = new Intent(context, StoreManageActivity.class);
			startActivity(intent);
			break;
		case 4: // 账号管理
			intent = new Intent(context, MemberManagerActivity.class);
			startActivity(intent);
			break;
		case 5: // 查看评论
			intent = new Intent(context, CommentManagerActivity.class);
			startActivity(intent);
			break;
		case 6: // 门户页面设置
			intent = new Intent(context, PortalImageActivity.class);
			startActivity(intent);
			break;
		case 7:// 客流情况
			intent = new Intent(context, GuestStateActivity.class);
			startActivity(intent);
			break;
		}
	}

	/**
	 * 获得模块打开时间,并去掉右上角更新图标
	 * 
	 * @param index
	 */
	/*@SuppressLint("SimpleDateFormat")
	private void getOpenTime(int index) {
		// TODO Auto-generated method stub
		//Constants.MAIN_ITEM_IS_UPDATE[index] = "0";
		Editor editor = getSharedPreferences("config", Context.MODE_PRIVATE)
				.edit();
		// String currentTime = Long.toString(System.currentTimeMillis());
		String currentTime = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss")
				.format(new Date());
		switch (index) {
		case 0:
			editor.putString("productFreshTime", currentTime).commit();
			break;
		case 1:
			editor.putString("noticeFreshTime", currentTime).commit();
			break;
		case 2:
			editor.putString("inNewsFreshTime", currentTime).commit();
			break;
		case 3:
			editor.putString("coNewsFreshTime", currentTime).commit();
			break;
		case 7:
			editor.putString("messageFreshTime", currentTime).commit();
			break;
		default:
			break;
		}

	}*/

	public void back(View v) {
		// 退出
		new AlertDialog.Builder(this).setTitle("是否确定退出?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						finish();
					}
				}).setNegativeButton("取消", null).show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	public void init() {
		// weatherText = (TextView) findViewById(R.id.weather_text);

		relate = (RelativeLayout) findViewById(R.id.relate);
		lst_views = (ScrollLayout) findViewById(R.id.views);
		tv_page = (TextView) findViewById(R.id.tv_page);
		tv_page.setText("1");
		Configure.init(this);
		param = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		param.rightMargin = 200;
		param.leftMargin = 60;
		if (gridView != null) {
			lst_views.removeAllViews();
		}

	}

	public void initData() {
		Configure.countPages = (int) Math.ceil(lstDate.size()
				/ (float) PAGE_SIZE);

		lists = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < Configure.countPages; i++) {
			lists.add(new ArrayList<String>());
			for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > lstDate
					.size() ? lstDate.size() : PAGE_SIZE * (i + 1)); j++)
				lists.get(i).add(lstDate.get(j));
		}
		boolean isLast = true;
		for (int i = lists.get(Configure.countPages - 1).size(); i < PAGE_SIZE; i++) {
			if (isLast) {
				lists.get(Configure.countPages - 1).add(null);
				isLast = false;
			} else
				lists.get(Configure.countPages - 1).add("none");
		}
	}

	public void CleanItems() {
		lstDate = new ArrayList<String>();
		for (int i = 0; i < lists.size(); i++) {
				if (lists.get(i) != null
						&& !lists.get(i).equals("none")) {
					lstDate.add(lists.get(i).toString());
					// System.out.println("-->" +
					// lists.get(i).get(j).toString());
				}
		}
		initData();
		lst_views.removeAllViews();
		gridviews = new ArrayList<DragGrid>();
		for (int i = 0; i < Configure.countPages; i++) {
			lst_views.addView(addGridView(i));
		}
		isClean = false;
		lst_views.snapToScreen(0);
	}

	public int getFristNonePosition(ArrayList<String> array) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i) != null && array.get(i).toString().equals("none")) {
				return i;
			}
		}
		return -1;
	}

	public int getFristNullPosition(ArrayList<String> array) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i) == null) {
				return i;
			}
		}
		return -1;
	}

	public LinearLayout addGridView(int i) {
		// if (lists.get(i).size() < PAGE_SIZE)
		// lists.get(i).add(null);

		linear = new LinearLayout(context);
		gridView = new DragGrid(context);
		adapter = new DateAdapter(this, lists.get(i));
		/**
		 * 查看各模块是否有更新
		 */
		// ShowMainItemUpdate.newInstance(this, adapter).execute();
		gridView.setAdapter(adapter);
		gridView.setNumColumns(2);
		gridView.setHorizontalSpacing(10);
		gridView.setVerticalSpacing(10);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index = (int) arg3;
				// 启动各个模块
				startActivity(index);

			}

		});
		gridView.setSelector(R.anim.grid_light);
		gridView.setPageListener(new DragGrid.G_PageListener() {
			@Override
			public void page(int cases, int page) {
				switch (cases) {
				case 0:// 滑动页面
					lst_views.snapToScreen(page);
					setCurPage(page);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Configure.isChangingPage = false;
						}
					}, 800);
					break;
				case 1:// 删除按钮上来
						// delImage.setBackgroundResource(R.drawable.del);
						// delImage.setVisibility(0);//注释把删除按钮隐藏
					delImage.startAnimation(up);
					break;
				case 2:// 删除按钮变深
					delImage.setBackgroundResource(R.drawable.del_check);
					Configure.isDelDark = true;
					break;
				case 3:// 删除按钮变淡
						// delImage.setBackgroundResource(R.drawable.del);
					Configure.isDelDark = false;
					break;
				case 4:// 删除按钮下去
					delImage.startAnimation(down);
					break;
				case 5:// 松手动作
					delImage.startAnimation(down);
					// Configure.isDelRunning = false;
					lists.get(Configure.curentPage).add(Configure.removeItem,
							null);
					lists.get(Configure.curentPage).remove(
							Configure.removeItem + 1);
					((DateAdapter) ((gridviews.get(Configure.curentPage))
							.getAdapter())).notifyDataSetChanged();
					break;
				}
			}
		});
		gridView.setOnItemChangeListener(new DragGrid.G_ItemChangeListener() {
			@Override
			public void change(int from, int to, int count) {
				String toString = (String) lists.get(
						Configure.curentPage - count).get(from);

				lists.get(Configure.curentPage - count).add(from,
						(String) lists.get(Configure.curentPage).get(to));
				lists.get(Configure.curentPage - count).remove(from + 1);
				lists.get(Configure.curentPage).add(to, toString);
				lists.get(Configure.curentPage).remove(to + 1);

				((DateAdapter) ((gridviews.get(Configure.curentPage - count))
						.getAdapter())).notifyDataSetChanged();
				((DateAdapter) ((gridviews.get(Configure.curentPage))
						.getAdapter())).notifyDataSetChanged();
			}
		});
		gridviews.add(gridView);
		linear.addView(gridviews.get(i), param);
		return linear;
	}

	public void runAnimation() {
		down = AnimationUtils.loadAnimation(context, R.anim.del_down);
		up = AnimationUtils.loadAnimation(context, R.anim.del_up);
		down.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				delImage.setVisibility(8);
			}
		});

		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(25000);
		left.setDuration(25000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(right);
			}
		});
		runImage.startAnimation(right);

	}

	public void setCurPage(final int page) {
		Animation a = AnimationUtils.loadAnimation(context,
				R.anim.scale_in);
		a.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				tv_page.setText((page + 1) + "");
				tv_page.startAnimation(AnimationUtils.loadAnimation(
						context, R.anim.scale_out));
			}
		});
		tv_page.startAnimation(a);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sm.unregisterListener(lsn);
	}


	

	

	public void restartApplication() {
		finish();
		Intent intent = getPackageManager().getLaunchIntentForPackage(
				getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// KeyEvent.KEYCODE_BACK代表返回操作.
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 处理返回操作.
			if (okToExit)
				this.finish();
			else {
				okToExit = true;
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					public void run() {
						okToExit = false;
					}
				}, 2000); // 2秒后重置
			}
		}
		return true;
	}
}
