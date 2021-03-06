package com.zhaowenbin.wisdombj.pager.content;

import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.pager.base.BasePager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTopTitle.setText("设置");
		TextView textView = new TextView(mActivity);
		textView.setTextSize(22);
		textView.setTextColor(new Color().RED);
		textView.setGravity(Gravity.CENTER);
		textView.setText("设置");
		flTabContent.addView(textView);
		ivLeftMenuBtn.setVisibility(View.INVISIBLE);
	}

}
