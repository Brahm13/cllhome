package com.cllhome;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class HomeService extends Service {
	private int statusBarHeight;
	private View view;
	private boolean viewAdded = false;
	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private BroadcastReceiver r;

	private DevicePolicyManager policyManager;
	private ComponentName componentName;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		view = LayoutInflater.from(this).inflate(R.layout.main, null);
		windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);

		layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_SYSTEM_ERROR,
				LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		layoutParams.gravity = Gravity.LEFT | Gravity.CENTER;

		view.setOnTouchListener(new OnTouchListener() {
			float[] temp = new float[] { 0f, 0f };
			boolean moved = false;
			long ts = 0;
			boolean ignoreThis = false;
			Handler handler = new Handler();
			Runnable r = new Runnable() {

				public void run() {
					if (ignoreThis) {
						ignoreThis = true;
						return;
					}
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addCategory(Intent.CATEGORY_HOME);
					startActivity(intent);
				}
			};

			public boolean onTouch(View v, MotionEvent event) {
				layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
				int eventaction = event.getAction();
				switch (eventaction) {
				case MotionEvent.ACTION_DOWN:
					moved = false;
					temp[0] = event.getX();
					temp[1] = event.getY();
					return true;

				case MotionEvent.ACTION_MOVE:
					refreshView((int) (event.getRawX() - temp[0]),
							(int) (event.getRawY() - temp[1]));
					moved = true;
					return true;

				case MotionEvent.ACTION_UP:
					if (!moved) {
						ignoreThis = false;
						long cur = SystemClock.uptimeMillis();
						if (Prefs.isEnableLock(getBaseContext())
								&& cur - ts < 300) {
							System.out.println(cur - ts);
							ignoreThis = true;
							lock();
							return true;
						}
						ts = cur;
						handler.postDelayed(r, 300);
						return true;
					}
				}
				return false;
			}
		});

		r = new Hallo(this);
		registerReceiver(r, new IntentFilter(Intent.ACTION_USER_PRESENT));
		registerReceiver(r, new IntentFilter(Intent.ACTION_SCREEN_OFF));

		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, AdminReceiver.class);
	}

	private void lock() {
		boolean active = policyManager.isAdminActive(componentName);
		if (active) {
			policyManager.lockNow();
		}
	}

	public void refreshView(int x, int y) {
		if (statusBarHeight == 0) {
			View rootView = view.getRootView();
			Rect r = new Rect();
			rootView.getWindowVisibleDisplayFrame(r);
			statusBarHeight = r.top;
		}

		layoutParams.x = x;
		layoutParams.y = y - statusBarHeight;
		refresh();
	}

	public void refresh() {
		if (viewAdded) {
			windowManager.updateViewLayout(view, layoutParams);
		} else {
			windowManager.addView(view, layoutParams);
			viewAdded = true;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		refresh();
		return START_STICKY;
	}

	public void removeView() {
		if (viewAdded) {
			windowManager.removeView(view);
			viewAdded = false;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		removeView();
		unregisterReceiver(r);
	}

}