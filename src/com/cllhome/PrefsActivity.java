package com.cllhome;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PrefsActivity extends Activity {
	private DevicePolicyManager policyManager;
	private ComponentName componentName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref);
		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, AdminReceiver.class);

		CheckBox c = (CheckBox) findViewById(R.id.enableLock);
		c.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					if (!policyManager.isAdminActive(componentName)) {
						activeManage();
					}
				}
				Prefs.setEnableLock(getBaseContext(), isChecked);
			}
		});
		c.setSelected(policyManager.isAdminActive(componentName));

		// start home service
		Intent i = new Intent();
		i.setAction("com.cllhome.home");
		startService(i);
	}

	private void activeManage() {
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"------ 其他描述 ------");

		startActivityForResult(intent, 0);
	}
}
