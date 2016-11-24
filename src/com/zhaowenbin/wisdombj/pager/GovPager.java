package com.zhaowenbin.wisdombj.pager;

import com.zhaowenbin.wisdombj.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class GovPager extends BasePager {

	public GovPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTopTitle.setText("政务");
		TextView textView = new TextView(mActivity);
		textView.setTextColor(new Color().RED);
		textView.setTextSize(22);
		textView.setGravity(Gravity.CENTER);
		textView.setText("政务");
		flTabContent.addView(textView);
	}

}
