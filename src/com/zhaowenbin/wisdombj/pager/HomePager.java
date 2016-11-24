package com.zhaowenbin.wisdombj.pager;

import com.zhaowenbin.wisdombj.R;

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
		tvTopTitle.setText("Ê×Ò³");
		TextView textView = new TextView(mActivity);
		textView.setTextColor(new Color().RED);
		textView.setTextSize(22);
		textView.setGravity(Gravity.CENTER);
		textView.setText("Ê×Ò³");
		flTabContent.addView(textView);
		ivLeftMenuBtn.setVisibility(View.INVISIBLE);
	}

}
