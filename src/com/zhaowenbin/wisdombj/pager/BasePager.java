package com.zhaowenbin.wisdombj.pager;

import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.activity.MainActivity;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasePager {
	public Activity mActivity;
	public TextView tvTopTitle;
	public FrameLayout flTabContent;
	public View mRootView;
	public ImageButton ivLeftMenuBtn;

	public BasePager(Activity activity){
		this.mActivity = activity;
		mRootView = initView();
	}
	
	public View initView(){
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		tvTopTitle = (TextView) view.findViewById(R.id.tv_top_title);
		flTabContent = (FrameLayout) view.findViewById(R.id.fl_tab_content);
		ivLeftMenuBtn = (ImageButton) view.findViewById(R.id.iv_left_menu_btn);
		ivLeftMenuBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)mActivity).toggle();
			}
		});
		return view;
	}
	
	public void initData(){
		
	}
}
