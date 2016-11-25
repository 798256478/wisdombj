package com.zhaowenbin.wisdombj.pager.base;

import android.app.Activity;
import android.view.View;

public abstract class NewsBasePager {
	public Activity mActivity;
	public View mRootView;

	public NewsBasePager(Activity mActivity){
		this.mActivity = mActivity;
		this.mRootView = initView();
	}

	public abstract View initView();
	
	public void initData(){
		
	}
}
