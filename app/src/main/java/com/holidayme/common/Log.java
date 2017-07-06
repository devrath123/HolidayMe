  package com.holidayme.common;


public class Log {

	private static boolean sIsLogEnabled = false;

	public static void i(final String key, final String message) {
		if (sIsLogEnabled) {
			android.util.Log.i(key, message);
		}
	}


	public static void v(final String key, final String message) {
		if (sIsLogEnabled) {
			android.util.Log.v(key, message);
		}
	}


	public static void d(final String key, final String message) {
		if (sIsLogEnabled) {
			android.util.Log.d(key, message);
		}
	}


	public static void w(final String key, final String message) {
		if (sIsLogEnabled) {
			android.util.Log.w(key, message);
		}
	}

	public static void e(final String key, final String message) {
		if (sIsLogEnabled) {
			android.util.Log.e(key, message);
		}
	}
}
