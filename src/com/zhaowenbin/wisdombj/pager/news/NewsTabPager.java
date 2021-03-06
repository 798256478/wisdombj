package com.zhaowenbin.wisdombj.pager.news;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.activity.NewDetailActivity;
import com.zhaowenbin.wisdombj.adapter.CarouseAdapter;
import com.zhaowenbin.wisdombj.adapter.NewsListAdapter;
import com.zhaowenbin.wisdombj.domain.NewsDataInfo.NewsChildrenData;
import com.zhaowenbin.wisdombj.domain.NewsTabDataBean;
import com.zhaowenbin.wisdombj.pager.base.NewsBasePager;
import com.zhaowenbin.wisdombj.utils.ConstantUtil;
import com.zhaowenbin.wisdombj.utils.SpUtil;
import com.zhaowenbin.wisdombj.view.CarouseViewPager;
import com.zhaowenbin.wisdombj.view.PullToRefreshListView;
import com.zhaowenbin.wisdombj.view.PullToRefreshListView.OnUpdateStateListener;

public class NewsTabPager extends NewsBasePager {

	private NewsChildrenData mTabData;
	private NewsTabDataBean newsTabDataBean;
	private CarouseViewPager cvpLunbo;
	private TextView tvNewsTitle;
	private CirclePageIndicator indicator;
	private PullToRefreshListView ptrlvNews;
	private View mHeaderView;
	private Timer timer;
	private int INIT = 0;
	private int REFLUSH = 1;
	private int MORE = 2;
	private NewsListAdapter newsListAdapter;
	private CarouseAdapter carouseAdapter;
	private String ids = "";

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
		ptrlvNews = (PullToRefreshListView) view.findViewById(R.id.ptrlv_news);
		
		ptrlvNews.addHeaderView(mHeaderView);
		
		ptrlvNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ids = SpUtil.getString(mActivity, ConstantUtil.READ_NEWS_ID, "");
				if(!ids.contains(newsTabDataBean.data.news.get(position - ptrlvNews.getHeaderViewsCount()).id + "")){
					ids += newsTabDataBean.data.news.get(position - ptrlvNews.getHeaderViewsCount()).id + ",";
				}
				SpUtil.putString(mActivity, ConstantUtil.READ_NEWS_ID, ids);
				TextView tvNewTitle = (TextView) view.findViewById(R.id.tv_new_title);
				tvNewTitle.setTextColor(Color.GRAY);
				Intent intent = new Intent(mActivity.getApplicationContext(), NewDetailActivity.class);
				intent.putExtra("url", newsTabDataBean.data.news.get(position - ptrlvNews.getHeaderViewsCount()).url);
				mActivity.startActivity(intent);
			}
		});
		
		ptrlvNews.setOnUpdateStateListener(new OnUpdateStateListener(){
			public void onUpdateState(){
				getDatafromService(REFLUSH);
			}
			
			public void onLoadState(){
				getDatafromService(MORE);
			}
		});
		
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
		
		cvpLunbo.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					timer.cancel();
					break;
				case MotionEvent.ACTION_MOVE:
					
					break;
				case MotionEvent.ACTION_UP:
					setTimmer();
					break;					
				default:
					break;
				}
				return false;
			}
		});
		return view;
	}
	
	@Override
	public void initData() {
		if(timer != null && cvpLunbo != null){
			timer.cancel();
			cvpLunbo.setCurrentItem(0);
		}
		getDatafromService(INIT);
	}

	private void getDatafromService(final int State) {
		String url;
		if(State == MORE){
			url = newsTabDataBean.data.more;
		}else {
			url = mTabData.url;
		}
		
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, ConstantUtil.SERVICE_URL + url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				Gson gson = new Gson();
				if(State == MORE){
				    NewsTabDataBean moreTabDataBean = gson.fromJson(result, NewsTabDataBean.class);
					newsTabDataBean.data.news.addAll(moreTabDataBean.data.news);
					newsListAdapter.notifyDataSetChanged();
					ptrlvNews.loadCompleted();
				}else {
					newsTabDataBean = gson.fromJson(result, NewsTabDataBean.class);
				}
				if(State == INIT){
					carouseAdapter = new CarouseAdapter(newsTabDataBean, mActivity, tvNewsTitle);
					cvpLunbo.setAdapter(carouseAdapter);
					indicator.setViewPager(cvpLunbo);
					newsListAdapter = new NewsListAdapter(newsTabDataBean, mActivity);
					ptrlvNews.setAdapter(newsListAdapter);
					tvNewsTitle.setText(newsTabDataBean.data.topnews.get(0).title);
					setTimmer();
				} else if (State == REFLUSH) {
					carouseAdapter.notifyDataSetChanged();
					newsListAdapter.notifyDataSetChanged();
					ptrlvNews.updateCompleted();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, "无网络连接，请检查网络设置", Toast.LENGTH_SHORT);
				Log.i("NewsTabPager", "网络连接失败");
			}
		});
	}
	
	private void setTimmer() {
		timer = new Timer();
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
}
