package com.zhaowenbin.wisdombj.pager.content;

import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.pager.base.BasePager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class WisdomPager extends BasePager {

	public WisdomPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTopTitle.setText("智慧服务");
		TextView textView = new TextView(mActivity);
		textView.setTextSize(22);
		textView.setTextColor(new Color().RED);
		textView.setGravity(Gravity.CENTER);
		textView.setText("智慧服务");
		flTabContent.addView(textView);
	}

}
