package com.cllhome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Hallo extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent();
		i.setAction("com.cllhome.home");
		context.startService(i);
	}

}
