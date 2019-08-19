package com.ko.navigation.utils;

import android.util.Log;

import com.ko.navigation.BuildConfig;

/**
 * @author lxm
 * @version 2019/8/19-15:51
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
public class L {
	public static final String TAG = "hyman";

	public static boolean sDebug = BuildConfig.DEBUG;

	public static void d(String msg , Object... args){
		if (!sDebug) {
			return;
		}
		Log.d(TAG, String.format( msg,args ) );
	}
}
