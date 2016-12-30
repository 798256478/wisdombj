package com.zhaowenbin.wisdombj.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class NetCacheUtil {
	
	private HttpURLConnection conn;
	private MemeoryCacheUtil mMemoryCacheUtil;
	private LoaclCacheUtil mLocalCacheUtil;

	public NetCacheUtil(MemeoryCacheUtil mMemeoryCacheUtil, LoaclCacheUtil mLoaclCacheUtil) {
		this.mMemoryCacheUtil = mMemeoryCacheUtil;
		this.mLocalCacheUtil = mLoaclCacheUtil;
	}

	public void getBitMapFromNet(ImageView ivImage, String url) {
		//AsyncTask 异步封装的工具， 可以实现异步请求及主界面的更新（对线程池和handler的封装）
		new BitMapTask().execute(ivImage, url);//启动AsyncTask
	}
	
	/*
	 * 三个泛型的意义，第一个泛型：doInBackground里的参数类型 第二个泛型：onProgressUpdate里的参数类型 第三个泛型：onPostExecute里的参数类型及doInbackground里的返回类型
	 *
	 */
	class BitMapTask extends AsyncTask<Object, Integer, Bitmap> {
		private ImageView imageView;
		private String url;

		@Override//预加载，运行在主线程
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override//正在加载，运行在子线程，可以直接异步请求
		protected Bitmap doInBackground(Object... params) {
			imageView = (ImageView)params[0];
			url = (String)params[1];
			imageView.setTag(url); //打标记，将当前imageView和url绑定在一起
			
			Bitmap bitmap = download(url);
			return bitmap;
		}
		//跟新进度的方法，运行在主线程
		protected void onProgressUpdate(Integer... values) {
			Log.i("NetCacheUtils", values + "");
		}
		//加载结束，运行在主线程，返回结果，可以更新UI
		protected void onPostExecute(Bitmap result) {
			if(result != null){
				String url = (String)imageView.getTag();
				if(url.equals(this.url)){
					imageView.setImageBitmap(result);
					
					
					//写本地缓存
					mLocalCacheUtil.setLocalCache(url, result);
					
					//写内存缓存
					mMemoryCacheUtil.setMemoryCache(url, result);
				}
			}
			super.onPostExecute(result);
		}
		
	}

	public Bitmap download(String url) {
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);//连接超时
			conn.setReadTimeout(5000);//读取超时
			
			conn.connect();
			
			int responseCode = conn.getResponseCode();
			
			if(responseCode == 200){
				InputStream inputStream = conn.getInputStream();
				
				//根据输入流生成bitmap对象
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.disconnect();
			}
		}
		return null;
	}
}
