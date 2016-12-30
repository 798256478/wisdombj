package com.zhaowenbin.wisdombj.utils;

import android.R.integer;
import android.graphics.Bitmap;
import android.util.LruCache;

public class MemeoryCacheUtil {
	/*
	 * 内存缓存
	 * 因为Android 2.3 （API level 9 ）开始，垃圾回收期会更倾向于回收持有软引用或弱引用的对象，这让软引用和弱引用变得不再可靠，google建议使用LruCache
	 *
	 */
	
	private LruCache<String, Bitmap> mMemroyCache;

	public MemeoryCacheUtil(){
		//LruCache可以将最近最少使用的对象回收掉，从而保证内存不会超出范围
		//Lru：least recentlly used 最近最少使用算法
		long maxMemory = Runtime.getRuntime().maxMemory();//获取分配给app的内存大小
		//初始化这个cache前需要设定这个cache的大小，这里的大小官方推荐是用当前app可用内存的八分之一
		mMemroyCache = new LruCache<String, Bitmap>((int)(maxMemory / 8)){
			//这个方法默认返回的是你缓存的item的数目，如果你想要自定义size的大小，直接重写这个方法
			protected int sizeOf(String key, Bitmap value) {
				int byteCount = value.getRowBytes() * value.getHeight();//计算图片大小：每行字节数 * 高度 
				return byteCount;
			};
		};
	}
	
	public void setMemoryCache(String url, Bitmap bitmap){
		mMemroyCache.put(url, bitmap);
	}
	
	public Bitmap getMemoryCache(String url){
		return mMemroyCache.get(url);
	}
}
