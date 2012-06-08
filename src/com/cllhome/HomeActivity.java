package com.cllhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// start home service
		Intent i = new Intent();
		i.setAction("com.cllhome.home");
		startService(i);
	}
}
