package com.zzy.net;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


public class NetService extends Service
{
	private static final int ONGOING_NOTIFICATION_ID = 101;
	private NetFile netFile;
	@Override
	public void onCreate()
	{
		netFile = new NetFile(this);
		netFile.start();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			startInForegroundO();
		else
			startInForegroundN();
	}

	// not test
	private void startInForegroundN() {
		String name = "IP-Monitor";
		String description = "收集应用网络访问";
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.app_head)
				.setContentTitle(name)
				.setContentText(description);
		mBuilder.setTicker(name);//第一次提示消息的时候显示在通知栏上
		mBuilder.setNumber(12);
		mBuilder.setAutoCancel(true);//自己维护通知的消失

		startForeground(ONGOING_NOTIFICATION_ID, mBuilder.build());
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	private void startInForegroundO() {
		String name = "IP-Monitor";
		String description = "收集应用网络访问";
		String channelId = this.getClass().toString();
		int importance = NotificationManager.IMPORTANCE_HIGH;//重要性级别
		NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
		mChannel.setDescription(description);
		mChannel.enableLights(true);
		mChannel.enableVibration(true);

		NotificationManager notificationManager = (NotificationManager)getSystemService(
				Context.NOTIFICATION_SERVICE);
		notificationManager.createNotificationChannel(mChannel);

		Notification notification = new Notification.Builder(this, channelId)
				.setContentTitle(name)
				.setContentText(description)
				.setSmallIcon(R.drawable.app_head)
				.setTicker(getText(R.string.abc_action_bar_home_description))
				.build();

		startForeground(ONGOING_NOTIFICATION_ID, notification);
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return super.onStartCommand(intent,flags,startId);
	}

	@Override
	public boolean onUnbind(Intent intent)
	{
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		netFile.stop();
		netFile = null;
	}
}


