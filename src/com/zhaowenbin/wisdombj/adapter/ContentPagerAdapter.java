package com.zhaowenbin.wisdombj.adapter;

import java.util.List;

import com.zhaowenbin.wisdombj.pager.BasePager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ContentPagerAdapter extends PagerAdapter {

	private List<BasePager> contentPagers;

	public ContentPagerAdapter(List<BasePager> contentPagers) {
		this.contentPagers = contentPagers;
	}

	@Override
	public int getCount() {
		return contentPagers.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		View view = contentPagers.get(position).mRootView;
		container.addView(view);
		return view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
