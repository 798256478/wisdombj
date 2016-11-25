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
		//��ʼ��fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		//��������
		transaction = fragmentManager.beginTransaction();
		//�滻����
		transaction.replace(R.id.fl_main, new ContentFragment(), CONTENT_FRAGMENT);
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), LEFT_MENU_FRAGMENT);
		//�ύ����
		transaction.commit();
	}

	private void initSlidingMenu() {
		//���ò����
		setBehindContentView(R.layout.left_menu);
		SlidingMenu menu = getSlidingMenu();
		//���ò໬�Ժ�Ԥ���Ŀ��
		menu.setBehindOffset(350);
		//���û����ķ����󻬻��һ�
		menu.setMode(SlidingMenu.LEFT);
		//���ÿ���ק�ķ�Χ��Ĭ��ֻ�б�Ե����ק
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//���ý��뽥��Ч����ֵ
		menu.setFadeDegree(0.35f);
		//�����·���ͼ�ڹ���ʽ�����ű���
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
