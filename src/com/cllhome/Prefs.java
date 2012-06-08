package com.cllhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefs {
	private static final String PREF_ENABLE_LOCK = "pref_enable_lock";

	static SharedPreferences getPrefs(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	static void setEnableLock(Context ctx, boolean enabled) {
		getPrefs(ctx).edit().putBoolean(PREF_ENABLE_LOCK, enabled).commit();
	}

	static boolean isEnableLock(Context ctx) {
		return getPrefs(ctx).getBoolean(PREF_ENABLE_LOCK, false);
	}
}
