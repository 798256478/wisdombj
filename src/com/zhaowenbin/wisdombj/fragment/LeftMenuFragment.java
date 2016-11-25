package com.zhaowenbin.wisdombj.fragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.activity.MainActivity;
import com.zhaowenbin.wisdombj.adapter.LeftMenuAdapter;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo;
import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LeftMenuFragment extends BaseFragment {

	private ListView lvLeftMenu;
	private LeftMenuAdapter mLeftMenuAdapter;
	public static int mCurrentSelected = 0; 

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		lvLeftMenu = (ListView) view.findViewById(R.id.lv_left_menu);
		lvLeftMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentSelected = position;
				mLeftMenuAdapter.notifyDataSetChanged();
				setContentView(position);
				toggle();
			}
		});
		return view;
	}

	protected void setContentView(int position) {
		Fragment contentFragment = ((MainActivity)mActivity).getContentFragment();
		((ContentFragment)contentFragment).setNewsContentPager(position);
	}  

	protected void toggle() {
		SlidingMenu slidingMenu = ((MainActivity)mActivity).getSlidingMenu();
		slidingMenu.toggle();
	}

	@Override
	public void initData() {
	}

	public void setMenuData(NewsDataInfo dataInfo) {
		mLeftMenuAdapter = new LeftMenuAdapter(dataInfo, mActivity);
		lvLeftMenu.setAdapter(mLeftMenuAdapter);
	}

}
