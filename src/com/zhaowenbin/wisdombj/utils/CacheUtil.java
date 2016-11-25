package com.zhaowenbin.wisdombj.utils;

import android.content.Context;

public class CacheUtil {
	public static void setCache(Context context, String key, String value){
		SpUtil.putString(context, key, value);
	}
	
	public static String getCache(Context context, String key){
		return SpUtil.getString(context, key, null);
	}
}
