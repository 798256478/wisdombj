package com.zhaowenbin.wisdombj.pager.content;

import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.pager.base.BasePager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class HomePager extends BasePager {

	public HomePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTopTitle.setText("首页");
		TextView textView = new TextView(mActivity);
		textView.setTextColor(new Color().RED);
		textView.setTextSize(22);
		textView.setGravity(Gravity.CENTER);
		textView.setText("首页");
		flTabContent.addView(textView);
		ivLeftMenuBtn.setVisibility(View.INVISIBLE);
	}

}
