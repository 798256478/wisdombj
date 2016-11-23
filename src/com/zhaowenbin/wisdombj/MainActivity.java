package com.zhaowenbin.wisdombj;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends SlidingFragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		initView();
	}

	private void initView() {
		//…Ë÷√≤‡±ﬂ¿∏
		setBehindContentView(R.layout.layout_left_menu);
		SlidingMenu menu = getSlidingMenu();
		menu.setBehindOffset(150);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMenu(R.layout.layout_left_menu);
		menu.setFadeDegree(0.35f);
	}
}
