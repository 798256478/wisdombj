package com.zhaowenbin.wisdombj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class LoaclCacheUtil {
	private static final String LOCAL_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache";
	
	public void setLocalCache(String url, Bitmap bitmap){
		File dir = new File(LOCAL_CACHE_PATH);
		if(!dir.exists() || dir.isDirectory()){
			dir.mkdirs();
		}
		
		try {
			String fileName = MD5Util.encode(url);
			
			File cacheFile = new File(dir, fileName);
			
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Bitmap getLocalCache(String url){
		try {
			File cacheFile = new File(LOCAL_CACHE_PATH, MD5Util.encode(url));
			if(cacheFile.exists()){
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
