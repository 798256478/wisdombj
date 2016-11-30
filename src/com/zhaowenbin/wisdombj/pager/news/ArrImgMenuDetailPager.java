package com.zhaowenbin.wisdombj.pager.news;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.adapter.ArrImgListAdapter;
import com.zhaowenbin.wisdombj.domain.ArrayImgDataBean;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo;
import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;
import com.zhaowenbin.wisdombj.utils.ConstantUtil;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArrImgMenuDetailPager extends NewsBasePager implements OnClickListener{

	private View view;
	private ListView lvArrayImg;
	private GridView gvArrayImg;
	private ImageButton ibArrayImgType;
	private ArrayImgDataBean arrayImgDataBean;
	private ArrImgListAdapter arrImgListAdapter;
	private Boolean isListView = true;

	public ArrImgMenuDetailPager(Activity mActivity, ImageButton ibArrayImgType) {
		super(mActivity);
		this.ibArrayImgType = ibArrayImgType;
		ibArrayImgType.setOnClickListener(this);
	}

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.page_array_img, null);
		lvArrayImg = (ListView) view.findViewById(R.id.lv_array_img);
		gvArrayImg = (GridView) view.findViewById(R.id.gv_array_img);
		return view;
	}
	
	@Override
	public void initData() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, ConstantUtil.PHOTOS_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				Gson gson = new Gson();
				arrayImgDataBean = gson.fromJson(result, ArrayImgDataBean.class);
				arrImgListAdapter = new ArrImgListAdapter(arrayImgDataBean, mActivity);
				if(lvArrayImg != null && gvArrayImg != null){
					lvArrayImg.setAdapter(arrImgListAdapter);
					gvArrayImg.setAdapter(arrImgListAdapter);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, "无法连接网络，请检查网络设置", Toast.LENGTH_SHORT);
			}
		});
	}

	@Override
	public void onClick(View v) {
		if(isListView){
			lvArrayImg.setVisibility(View.INVISIBLE);
			gvArrayImg.setVisibility(View.VISIBLE);
			ibArrayImgType.setImageResource(R.drawable.icon_pic_list_type);
			isListView = false;
		} else {
			lvArrayImg.setVisibility(View.VISIBLE);
			gvArrayImg.setVisibility(View.INVISIBLE);
			ibArrayImgType.setImageResource(R.drawable.icon_pic_grid_type);
			isListView = true;
		}
	}

}
