package com.zhaowenbin.wisdombj.fragment;

import java.util.ArrayList;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.activity.MainActivity;
import com.zhaowenbin.wisdombj.adapter.ContentPagerAdapter;
import com.zhaowenbin.wisdombj.pager.BasePager;
import com.zhaowenbin.wisdombj.pager.GovPager;
import com.zhaowenbin.wisdombj.pager.HomePager;
import com.zhaowenbin.wisdombj.pager.NewsPager;
import com.zhaowenbin.wisdombj.pager.SettingPager;
import com.zhaowenbin.wisdombj.pager.WisdomPager;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {

	private ViewPager vpContent;
	private List<BasePager> contentPagers;
	private RadioGroup rgBottomTab;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		vpContent = (ViewPager) view.findViewById(R.id.vp_content);
		rgBottomTab = (RadioGroup) view.findViewById(R.id.rg_bottom_tab);
		rgBottomTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
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
				contentPagers.get(arg0).initData();
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
		contentPagers = new ArrayList<BasePager>();
		contentPagers.add(new HomePager(mActivity));
		contentPagers.add(new NewsPager(mActivity));
		contentPagers.add(new WisdomPager(mActivity));
		contentPagers.add(new GovPager(mActivity));
		contentPagers.add(new SettingPager(mActivity));
		vpContent.setAdapter(new ContentPagerAdapter(contentPagers));
		contentPagers.get(0).initData();
		setLeftMenuEnable(false);
	}
}
