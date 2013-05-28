package com.szkk.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.szkk.account.LoginActivity;
import com.szkk.net.SZHttpRequestManager;
import com.szkk.net.delegate.NotificationTaskDelegate;
import com.szkk.www.MainActivity;
import com.szkk.www.R;

public class NotificationService extends android.app.Service implements NotificationTaskDelegate{
	private static final  String PCK_NAME="com.szkk.www";
	private long interval = 3000l;
	private boolean finished = true;
	private long offset = 0;
	private int user_id = -1;
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		user_id = intent.getIntExtra("user_id", -1);
		offset = System.currentTimeMillis();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if(System.currentTimeMillis() - offset > interval && finished){
						Log.i("tag1", "finished " + finished);
						offset = System.currentTimeMillis();
						finished = false;
						SZHttpRequestManager.sharedManager().checkNotification(user_id, NotificationService.this);
					}
				}
			}
		}).start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("tag1", "onDestroy ");
	}



	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public void didFinishCheckNofity(boolean nofity) {
		finished = true;
		if(nofity){
			sendNotification();
		}
	}

	@Override
	public void didFailedCheckNofity() {
		finished = true;
	}
	
	private void sendNotification() {
		NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		String text = this.getResources().getString(R.string.notification_template);
		notification.tickerText = text;

		/***
		 * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，
		 * 该Intent会被触发 notification.contentView:我们可以不在状态栏放图标而是放一个view
		 * notification.deleteIntent 当当前notification被移除时执行的intent
		 * notification.vibrate 当手机震动时，震动周期设置
		 */
		// 添加声音提示
		notification.defaults = Notification.DEFAULT_ALL;
		// audioStreamType的值必须AudioManager中的值，代表着响铃的模式
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;

		// 下边的两个方式可以添加音乐
		// notification.sound =
		// Uri.parse("file:///sdcard/notification/ringer.mp3");
		// notification.sound =
		// Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		Intent intent = new Intent(); 
		intent = getPackageManager().getLaunchIntentForPackage(PCK_NAME);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_ONE_SHOT);
		
		String title = this.getResources().getString(R.string.notification_title);
		String content = this.getResources().getString(R.string.notification_content);
		notification.setLatestEventInfo(this, title, content, pendingIntent);
		manager.notify(1, notification);
	}
}
