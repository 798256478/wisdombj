package com.zhaowenbin.wisdombj.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.view.PullToRefreshListView.OnUpdateStateListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("NewApi")
public class PullToReflushView extends LinearLayout {

	private static final int PULL_TO_REFLUSH = 0;
	private static final int RELEASE_REFLUSH = 1;
	private static final int REFLUSH = 2;
	private View view;
	private ImageView ivPullArraw;
	private ProgressBar pbReflush;
	private TextView tvReflushTitle;
	private TextView tvReflushTime;
	private int padding;
	private RotateAnimation rotateUpAnimation;
	private RotateAnimation rotateDownAnimation;
	public PullToReflushView(Context context) {
		super(context);
		init();
	}

	public PullToReflushView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PullToReflushView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}


	private void init() {
		initHeader();
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initFooter();
	}

	private void initFooter() {
		footerView = View.inflate(getContext(), R.layout.footer_list_view, null);
		LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		footerView.setLayoutParams(params);
		pvLoadMore = (ProgressBar) footerView.findViewById(R.id.pv_load_more);
		tvLoadMore = (TextView) footerView.findViewById(R.id.tv_load_more);
		addView(footerView);
		footerView.measure(0, 0);
		mFooterPadding = - footerView.getMeasuredHeight();
		footerView.setPadding(0, 0, 0, 0);
	}

	private void initHeader() {
		view = View.inflate(getContext(), R.layout.header_list_view, null);
		LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(params);
		ivPullArraw = (ImageView) view.findViewById(R.id.iv_pull_arraw);
		pbReflush = (ProgressBar) view.findViewById(R.id.pb_reflush);
		tvReflushTitle = (TextView) view.findViewById(R.id.tv_reflush_title);
		tvReflushTime = (TextView) view.findViewById(R.id.tv_reflush_time);
		addView(view);
		view.measure(0, 0);
		padding = - view.getMeasuredHeight();
		view.setPadding(0, padding, 0, 0);
		rotateUpAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateUpAnimation.setDuration(1000);
		rotateUpAnimation.setFillAfter(true);
		rotateDownAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateUpAnimation.setDuration(1000);
		rotateUpAnimation.setFillAfter(true);
	}
	
	float startY = -1;
	private OnUpdateStateListener onUpdateStateListener;
	private View footerView;
	private ProgressBar pvLoadMore;
	private TextView tvLoadMore;
	private boolean isLoading;
	private int mFooterPadding;
	private int mCurrentState;
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if(startY == -1){
				startY = ev.getY();
			}
			float moveY = ev.getY();
			float dY = moveY - startY;
			if(dY > 0){
				Log.i("PullToReflushView", padding + "");
				int newPadding = (int) (padding + dY);
				if(newPadding >= 0 && mCurrentState != RELEASE_REFLUSH){
					mCurrentState  = RELEASE_REFLUSH;
					updateState();
				} else if(newPadding <0 && mCurrentState != PULL_TO_REFLUSH) {
					mCurrentState  = PULL_TO_REFLUSH;
					updateState();
				}
				view.setPadding(0, newPadding, 0, 0);
				return false;
			}
			break;
		case MotionEvent.ACTION_UP:
			if(mCurrentState == PULL_TO_REFLUSH){
				view.setPadding(0, padding, 0, 0);
			}else {
				if(onUpdateStateListener != null){
					onUpdateStateListener.onUpdateState();
				}
				view.setPadding(0, 0, 0, 0);
				mCurrentState = REFLUSH;
				updateState();
			}
			break;			
		case MotionEvent.ACTION_CANCEL:
			if(mCurrentState == PULL_TO_REFLUSH){
				view.setPadding(0, padding, 0, 0);
			}else {
				onUpdateStateListener.onUpdateState();
				view.setPadding(0, 0, 0, 0);
				mCurrentState = REFLUSH;
				updateState();
			}
			break;	
		}
		return false;
	}


	private void updateState() {
		switch (mCurrentState) {
		case PULL_TO_REFLUSH:
			pbReflush.setVisibility(View.INVISIBLE);
			ivPullArraw.startAnimation(rotateDownAnimation);
			tvReflushTitle.setText("下拉刷新");
			break;
		case RELEASE_REFLUSH:
			pbReflush.setVisibility(View.INVISIBLE);
			ivPullArraw.startAnimation(rotateUpAnimation);
			tvReflushTitle.setText("释放刷新");
			break;
		case REFLUSH:
			ivPullArraw.clearAnimation();
			ivPullArraw.setVisibility(View.INVISIBLE);
			pbReflush.setVisibility(View.VISIBLE);
			tvReflushTitle.setText("正在刷新...");
			break;		
		default:
			break;
		}
	}
	
	
	public interface OnUpdateStateListener{
		void onUpdateState();
		void onLoadState();
	}

	public void setOnUpdateStateListener(
			OnUpdateStateListener onUpdateStateListener) {
		this.onUpdateStateListener = onUpdateStateListener;
	}

	public void updateCompleted() {
		mCurrentState = PULL_TO_REFLUSH;
		pbReflush.setVisibility(View.INVISIBLE);
		ivPullArraw.setVisibility(View.VISIBLE);
		ivPullArraw.startAnimation(rotateDownAnimation);
		tvReflushTitle.setText("下拉刷新");
		tvReflushTime.setText(getCurrentTime());
		view.setPadding(0, padding, 0, 0);
	}
	
	public void loadCompleted(){
		isLoading = false;
		footerView.setPadding(0, mFooterPadding, 0, 0);
	}

	private String getCurrentTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(new Date());
		return currentTime;
	}

	

}
