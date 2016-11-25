package com.zhaowenbin.wisdombj.pager.news;

import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class InteractMenuDetailPager extends NewsBasePager {

	public InteractMenuDetailPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	public View initView() {
		TextView textView = new TextView(mActivity);
		textView.setTextColor(new Color().RED);
		textView.setTextSize(22);
		textView.setGravity(Gravity.CENTER);
		textView.setText("≤‡±ﬂ¿∏œÍ«È“≥-ª•∂Ø");
		return textView;
	}

}
