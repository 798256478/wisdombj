package com.zhaowenbin.wisdombj.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.zhaowenbin.wisdombj.R;

public class MyBitMapUtil {

	private NetCacheUtil mNetCacheUtils;
	private MemeoryCacheUtil mMemeoryCacheUtil;
	private LoaclCacheUtil mLoaclCacheUtil;

	public MyBitMapUtil() {
		mMemeoryCacheUtil = new MemeoryCacheUtil();
		mLoaclCacheUtil = new LoaclCacheUtil();
		mNetCacheUtils = new NetCacheUtil(mMemeoryCacheUtil, mLoaclCacheUtil);
	}

	public void configDefaultLoadingImage(int picItemListDefault) {
		
	}

	public void display(ImageView ivImage, String url) {
		ivImage.setImageResource(R.drawable.pic_item_list_default);
		
		//优先从内存中加载图片
		Bitmap bitmap = mMemeoryCacheUtil.getMemoryCache(url);
		if(bitmap != null){
			ivImage.setImageBitmap(bitmap);
			return;
		}
		
		//从本地加载图片
		bitmap = mLoaclCacheUtil.getLocalCache(url);
		if(bitmap != null){
			ivImage.setImageBitmap(bitmap);
			
			//写内存缓存
			mMemeoryCacheUtil.setMemoryCache(url, bitmap);
			return;
		}
		//从网络下载图片
		mNetCacheUtils.getBitMapFromNet(ivImage, url);
	}
	

}
