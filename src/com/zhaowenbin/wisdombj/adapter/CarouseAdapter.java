package com.zhaowenbin.wisdombj.adapter;

import com.lidroid.xutils.BitmapUtils;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.domain.NewsTabDataBean;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class CarouseAdapter extends PagerAdapter {

	private NewsTabDataBean newsTabDataBean;
	private Activity mActivity;
	private BitmapUtils mBitmapUtils;
	private TextView tvNewsTitle;

	public CarouseAdapter(NewsTabDataBean newsTabDataBean, Activity mActivity, TextView tvNewsTitle) {
		this.newsTabDataBean = newsTabDataBean;
		this.mActivity = mActivity;
		mBitmapUtils = new BitmapUtils(mActivity);
		this.tvNewsTitle = tvNewsTitle;
		mBitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
	}

	@Override
	public int getCount() {
		return newsTabDataBean.data.topnews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(mActivity);
		mBitmapUtils.display(imageView, newsTabDataBean.data.topnews.get(position).topimage);
		imageView.setScaleType(ScaleType.FIT_XY);
		container.addView(imageView);
		return imageView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

}
