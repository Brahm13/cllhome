package com.cllhome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Hallo extends BroadcastReceiver {
	private final HomeService hs;

	public Hallo(HomeService h) {
		this.hs = h;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
			hs.refresh();
		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			hs.removeView();
		}

	}
}
