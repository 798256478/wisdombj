package com.zhaowenbin.wisdombj.activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.fragment.ContentFragment;
import com.zhaowenbin.wisdombj.fragment.LeftMenuFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends SlidingFragmentActivity {
	private static final String CONTENT_FRAGMENT = "content_fragment";
	private static final String LEFT_MENU_FRAGMENT = "left_menu_fragment";
	private FragmentTransaction transaction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		initView();
		initSlidingMenu();
		initFragment();
	}

	private void initView() {

	}

	private void initFragment() {
		//初始化fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		//开启事务
		transaction = fragmentManager.beginTransaction();
		//替换布局
		transaction.replace(R.id.fl_main, new ContentFragment(), CONTENT_FRAGMENT);
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), LEFT_MENU_FRAGMENT);
		//提交事务
		transaction.commit();
	}

	private void initSlidingMenu() {
		//设置侧边栏
		setBehindContentView(R.layout.left_menu);
		SlidingMenu menu = getSlidingMenu();
		//设置侧滑以后预留的宽度
		menu.setBehindOffset(350);
		//设置滑动的方向，左滑或右滑
		menu.setMode(SlidingMenu.LEFT);
		//设置可拖拽的范围，默认只有边缘可拖拽
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		//设置下方视图在滚动式的缩放比例
		menu.setBehindScrollScale(0.0f);
	}
	
	public Fragment getSlidingMenuFragment(){
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		return supportFragmentManager.findFragmentByTag(LEFT_MENU_FRAGMENT);
	}
	
	public Fragment getContentFragment(){
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		return supportFragmentManager.findFragmentByTag(CONTENT_FRAGMENT);
	}
}
