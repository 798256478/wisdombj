package com.zhaowenbin.wisdombj.pager.news;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.adapter.CarouseAdapter;
import com.zhaowenbin.wisdombj.adapter.NewsListAdapter;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo.NewsChildrenData;
import com.zhaowenbin.wisdombj.domain.NewsTabDataBean;
import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;
import com.zhaowenbin.wisdombj.utils.ConstantUtil;
import com.zhaowenbin.wisdombj.view.CarouseViewPager;

public class NewsTabPager extends NewsBasePager {

	private NewsChildrenData mTabData;
	private NewsTabDataBean newsTabDataBean;
	private CarouseViewPager cvpLunbo;
	private TextView tvNewsTitle;
	private CirclePageIndicator indicator;
	private ListView lvNews;
	private View mHeaderView;

	public NewsTabPager(Activity mActivity, NewsChildrenData newsChildrenData) {
		super(mActivity);
		mTabData = newsChildrenData;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.page_news_tab, null);
		mHeaderView = View.inflate(mActivity, R.layout.list_header_carouse, null);
		cvpLunbo = (CarouseViewPager) mHeaderView.findViewById(R.id.cvp_lunbo);
		tvNewsTitle = (TextView) mHeaderView.findViewById(R.id.tv_news_title);
		indicator = (CirclePageIndicator) mHeaderView.findViewById(R.id.indicator);
		lvNews = (ListView) view.findViewById(R.id.lv_news);
		
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				tvNewsTitle.setText(newsTabDataBean.data.topnews.get(arg0).title);
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
	
	@Override
	public void initData() {
		getDatafromService();
	}

	private void getDatafromService() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, ConstantUtil.SERVICE_URL + mTabData.url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				Gson gson = new Gson();
				newsTabDataBean = gson.fromJson(result, NewsTabDataBean.class);
				CarouseAdapter carouseAdapter = new CarouseAdapter(newsTabDataBean, mActivity, tvNewsTitle);
				cvpLunbo.setAdapter(carouseAdapter);
				indicator.setViewPager(cvpLunbo);
				NewsListAdapter newsListAdapter = new NewsListAdapter(newsTabDataBean, mActivity);
				lvNews.setAdapter(newsListAdapter);
				lvNews.addHeaderView(mHeaderView);
				tvNewsTitle.setText(newsTabDataBean.data.topnews.get(0).title);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					
					private int currentItem;

					@Override
					public void run() {
						currentItem = cvpLunbo.getCurrentItem();
						if(currentItem == cvpLunbo.getAdapter().getCount() - 1){
							currentItem = -1;
						}
						mActivity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								cvpLunbo.setCurrentItem(currentItem + 1);
							}
						});
					}
				}, 3000, 3000);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.i("NewsTabPager", "Õ¯¬Á¡¨Ω” ß∞‹");
			}
		});
	}

}
