package com.zhaowenbin.wisdombj.adapter;

import com.lidroid.xutils.BitmapUtils;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.domain.ArrayImgDataBean;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ArrImgListAdapter extends BaseAdapter {

	private ArrayImgDataBean arrayImgDataBean;
	private Activity mActivity;
	private BitmapUtils mBitmapUtils;

	public ArrImgListAdapter(ArrayImgDataBean arrayImgDataBean,
			Activity mActivity) {
		this.arrayImgDataBean = arrayImgDataBean;
		this.mActivity = mActivity;
		mBitmapUtils = new BitmapUtils(mActivity);
		mBitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
	}

	@Override
	public int getCount() {
		return arrayImgDataBean.data.news.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayImgDataBean.data.news.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = View.inflate(mActivity, R.layout.item_array_img, null);
			viewHolder = new ViewHolder();
			viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		mBitmapUtils.display(viewHolder.ivImage, arrayImgDataBean.data.news.get(position).listimage);
		viewHolder.tvTitle.setText(arrayImgDataBean.data.news.get(position).title);
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView ivImage;
		public TextView tvTitle;
	} 

}
