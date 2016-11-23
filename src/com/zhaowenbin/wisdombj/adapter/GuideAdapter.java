package com.zhaowenbin.wisdombj.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideAdapter extends PagerAdapter {

	private List<ImageView> mImageViews;

	public GuideAdapter(List<ImageView> mImageViews) {
		this.mImageViews = mImageViews;
	}

	@Override
	public int getCount() {
		return mImageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = mImageViews.get(position);
		container.addView(imageView);
		return imageView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mImageViews.get(position));
	}

}
