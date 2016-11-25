package com.zhaowenbin.wisdombj.adapter;

import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo;
import com.zhaowenbin.wisdombj.fragment.LeftMenuFragment;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeftMenuAdapter extends BaseAdapter {
	
	private NewsDataInfo mDataInfo;
	private Activity mActivity;

	public LeftMenuAdapter(NewsDataInfo dataInfo, Activity mActivity) {
		this.mDataInfo = dataInfo;
		this.mActivity = mActivity;
	}

	@Override
	public int getCount() {
		return mDataInfo.data.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataInfo.data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
		TextView tvLeftMenu = (TextView) view.findViewById(R.id.tv_left_menu);
		tvLeftMenu.setText(mDataInfo.data.get(position).title);
		tvLeftMenu.setEnabled(position == LeftMenuFragment.mCurrentSelected);
		return view;
	}

}
