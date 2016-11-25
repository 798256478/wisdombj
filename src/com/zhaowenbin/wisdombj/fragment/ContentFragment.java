package com.zhaowenbin.wisdombj.fragment;

import java.util.ArrayList;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.activity.MainActivity;
import com.zhaowenbin.wisdombj.adapter.ContentPagerAdapter;
import com.zhaowenbin.wisdombj.pager.base.BasePager;
import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;
import com.zhaowenbin.wisdombj.pager.content.GovPager;
import com.zhaowenbin.wisdombj.pager.content.HomePager;
import com.zhaowenbin.wisdombj.pager.content.NewsPager;
import com.zhaowenbin.wisdombj.pager.content.SettingPager;
import com.zhaowenbin.wisdombj.pager.content.WisdomPager;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {

	private ViewPager vpContent;
	private List<BasePager> mContentPagers;
	private RadioGroup rgBottomTab;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		vpContent = (ViewPager) view.findViewById(R.id.vp_content);
		rgBottomTab = (RadioGroup) view.findViewById(R.id.rg_bottom_tab);
		rgBottomTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				LeftMenuFragment.mCurrentSelected = 0;
				switch (checkedId) {
				case R.id.rb_home:
					vpContent.setCurrentItem(0, false);
					setLeftMenuEnable(false);
					break;
				case R.id.rb_news:
					vpContent.setCurrentItem(1, false);
					setLeftMenuEnable(true);
					break;
				case R.id.rb_wisdom:
					vpContent.setCurrentItem(2, false);
					setLeftMenuEnable(true);
					break;
				case R.id.rb_gov:
					vpContent.setCurrentItem(3, false);
					setLeftMenuEnable(true);
					break;	
				case R.id.rb_setting:
					vpContent.setCurrentItem(4, false);
					setLeftMenuEnable(false);
					break;
				}
			}
		});
		
		vpContent.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				mContentPagers.get(arg0).initData();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		return view;
	}

	protected void setLeftMenuEnable(boolean enable) {
		SlidingMenu menu = ((MainActivity)mActivity).getSlidingMenu();
		if(enable){
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void initData() {
		mContentPagers = new ArrayList<BasePager>();
		mContentPagers.add(new HomePager(mActivity));
		mContentPagers.add(new NewsPager(mActivity));
		mContentPagers.add(new WisdomPager(mActivity));
		mContentPagers.add(new GovPager(mActivity));
		mContentPagers.add(new SettingPager(mActivity));
		vpContent.setAdapter(new ContentPagerAdapter(mContentPagers));
		mContentPagers.get(0).initData();
		setLeftMenuEnable(false);
	}
	
	public void setNewsContentPager(int position){
		((NewsPager)mContentPagers.get(1)).setSelectedMenuDetailPager(position);
	}
}
