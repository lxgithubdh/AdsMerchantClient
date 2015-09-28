package com.whut.component.receiver;

import com.whut.activity.MainActivity;
import com.whut.seller.R;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 接收vip到店广播，以通知栏的方式提醒
 * @author lx
 */
public class VipNoticeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String msg = intent.getExtras().getString("msg");
		NotificationManager manager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);
		Builder builder = new Builder(context).setTicker("Vip进店")
				.setSmallIcon(R.drawable.vip_suspension);
		Intent detail = new Intent(context,MainActivity.class);
		detail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pIntent = PendingIntent.getActivity(
				context, 0, detail, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pIntent).setContentTitle("提醒")
			.setContentText(msg);
		Notification note = builder.build();
		note.flags = Notification.FLAG_AUTO_CANCEL;
		manager.notify(0, note);
	}

}
