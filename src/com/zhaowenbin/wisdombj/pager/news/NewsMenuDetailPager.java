package com.zhaowenbin.wisdombj.pager.news;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.activity.MainActivity;
import com.zhaowenbin.wisdombj.adapter.NewsMenuPagerAdapter;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo;
import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;
import com.zhaowenbin.wisdombj.pager.content.NewsPager;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class NewsMenuDetailPager extends NewsBasePager{

	private ArrayList<NewsTabPager> newsTabPagers;
	private ViewPager vpNewsMenu;
	private TabPageIndicator indicator;
	private NewsDataInfo mDataInfo;
	private SlidingMenu menu;

	public NewsMenuDetailPager(Activity mActivity, NewsDataInfo dataInfo) {
		super(mActivity);
		mDataInfo = dataInfo;
		initData();
	}

	@Override
	public View initView() {
		menu = ((MainActivity)mActivity).getSlidingMenu();
		View view = View.inflate(mActivity, R.layout.page_news_menu, null);
		vpNewsMenu = (ViewPager) view.findViewById(R.id.vp_news_menu);
        indicator = (TabPageIndicator)view.findViewById(R.id.indicator);
        indicator.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				} else {
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}
			}
		});
        indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				newsTabPagers.get(arg0).initData();
				if(arg0 == 0){
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else {
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		return view;
	}
	
	@Override
	public void initData() {
		newsTabPagers = new ArrayList<NewsTabPager>();
		for (int i = 0; i < mDataInfo.data.get(0).children.size(); i++) {
			newsTabPagers .add(new NewsTabPager(mActivity, mDataInfo.data.get(0).children.get(i)));
		}
		NewsMenuPagerAdapter newsMenuPagerAdapter = new NewsMenuPagerAdapter(newsTabPagers, mDataInfo);
		vpNewsMenu.setAdapter(newsMenuPagerAdapter);
        indicator.setViewPager(vpNewsMenu);
        newsTabPagers.get(0).initData();
	}
}
