package com.zhaowenbin.wisdombj.adapter;

import com.lidroid.xutils.BitmapUtils;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.domain.NewsTabDataBean;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class NewsListAdapter extends BaseAdapter {

	private NewsTabDataBean newsTabDataBean;
	private Activity mActivity;
	private BitmapUtils mBitmapUtils;

	public NewsListAdapter(NewsTabDataBean newsTabDataBean, Activity mActivity) {
		this.newsTabDataBean =  newsTabDataBean;
		this.mActivity = mActivity;
		mBitmapUtils = new BitmapUtils(mActivity);
	}

	@Override
	public int getCount() {
		return newsTabDataBean.data.news.size();
	}

	@Override
	public Object getItem(int position) {
		return newsTabDataBean.data.news.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = View.inflate(mActivity, R.layout.item_news, null);
			viewHolder = new ViewHolder();
			viewHolder.ivNewImg = (ImageView) convertView.findViewById(R.id.iv_new_img);
			viewHolder.tvNewTitle = (TextView) convertView.findViewById(R.id.tv_new_title);
			viewHolder.tvUpdateTime = (TextView) convertView.findViewById(R.id.tv_update_time);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		mBitmapUtils.display(viewHolder.ivNewImg, newsTabDataBean.data.news.get(position).listimage);
		viewHolder.ivNewImg.setScaleType(ScaleType.CENTER_CROP);
		viewHolder.tvNewTitle.setText(newsTabDataBean.data.news.get(position).title);
		viewHolder.tvUpdateTime.setText(newsTabDataBean.data.news.get(position).pubdate);
		return convertView;
	}

	static class ViewHolder{
		public ImageView ivNewImg;
		public TextView tvNewTitle;
		public TextView tvUpdateTime;
	}
}
