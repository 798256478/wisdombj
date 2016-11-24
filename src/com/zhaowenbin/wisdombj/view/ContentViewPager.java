package com.zhaowenbin.wisdombj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ContentViewPager extends ViewPager {

	public ContentViewPager(Context context) {
		super(context);
	}

	public ContentViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return true;
	}

}
