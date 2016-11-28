package com.zhaowenbin.wisdombj.adapter;

import java.util.ArrayList;

import com.zhaowenbin.wisdombj.domain.NewsDataInfo;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo.NewsChildrenData;
import com.zhaowenbin.wisdombj.pager.content.NewsPager;
import com.zhaowenbin.wisdombj.pager.news.NewsTabPager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsMenuPagerAdapter extends PagerAdapter {

	private ArrayList<NewsTabPager> newsTabPagers;
	private NewsDataInfo mDataInfo;

	public NewsMenuPagerAdapter(ArrayList<NewsTabPager> newsTabPagers, NewsDataInfo mDataInfo) {
		this.newsTabPagers = newsTabPagers;
		this.mDataInfo = mDataInfo;
	}

	@Override
	public int getCount() {
		return newsTabPagers.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return mDataInfo.data.get(0).children.get(position).title;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = newsTabPagers.get(position).mRootView;
		newsTabPagers.get(position).initData();
		container.addView(view);
		return view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
