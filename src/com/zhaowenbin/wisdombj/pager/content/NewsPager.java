package com.zhaowenbin.wisdombj.pager.content;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.activity.MainActivity;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo;
import com.zhaowenbin.wisdombj.fragment.LeftMenuFragment;
import com.zhaowenbin.wisdombj.pager.base.BasePager;
import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;
import com.zhaowenbin.wisdombj.pager.news.ArrImgMenuDetailPager;
import com.zhaowenbin.wisdombj.pager.news.InteractMenuDetailPager;
import com.zhaowenbin.wisdombj.pager.news.NewsMenuDetailPager;
import com.zhaowenbin.wisdombj.pager.news.TopicMenuDetailPager;
import com.zhaowenbin.wisdombj.utils.CacheUtil;
import com.zhaowenbin.wisdombj.utils.ConstantUtil;

import android.R.integer;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NewsPager extends BasePager {
	private List<NewsBasePager> mNewsMenuPagers;
	private  NewsDataInfo dataInfo;
	
	public NewsPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTopTitle.setText("新闻");
		String cache = CacheUtil.getCache(mActivity, ConstantUtil.NEWS_MENU_URL);
		if(!TextUtils.isEmpty(cache)){
			parseJson(cache);
		}
		getNewsDataFromService();
	}

	private void getNewsDataFromService() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, ConstantUtil.NEWS_MENU_URL, new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
			}
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String jsonData = responseInfo.result;
				parseJson(jsonData);
				CacheUtil.setCache(mActivity, ConstantUtil.NEWS_MENU_URL, jsonData);
				mNewsMenuPagers = new ArrayList<NewsBasePager>();
				mNewsMenuPagers.add(new NewsMenuDetailPager(mActivity, dataInfo));
				mNewsMenuPagers.add(new TopicMenuDetailPager(mActivity));
				mNewsMenuPagers.add(new ArrImgMenuDetailPager(mActivity, ibArrayImgType));
				mNewsMenuPagers.add(new InteractMenuDetailPager(mActivity));
				setSelectedMenuDetailPager(0);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, "无网络连接，请检查网络设置", Toast.LENGTH_SHORT);
				Log.i("NewsPager", "网络连接失败");
			}
		});
	}

	protected void parseJson(String jsonData) {
		Gson gson = new Gson();
		dataInfo = gson.fromJson(jsonData, NewsDataInfo.class);
		Fragment slidingMenuFragment = ((MainActivity)mActivity).getSlidingMenuFragment();
		((LeftMenuFragment)slidingMenuFragment).setMenuData(dataInfo);
	}
	
	public void setSelectedMenuDetailPager(int position){
		flTabContent.removeAllViews();
		flTabContent.addView(mNewsMenuPagers.get(position).mRootView);
		tvTopTitle.setText(dataInfo.data.get(position).title);
		mNewsMenuPagers.get(position).initData();
		if(mNewsMenuPagers.get(position) instanceof ArrImgMenuDetailPager){
			ibArrayImgType.setVisibility(View.VISIBLE);
		} else {
			ibArrayImgType.setVisibility(View.INVISIBLE);
		}
	}

}
