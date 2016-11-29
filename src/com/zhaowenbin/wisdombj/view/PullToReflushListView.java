package com.zhaowenbin.wisdombj.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zhaowenbin.wisdombj.R;
import android.R.integer;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PullToReflushListView extends ListView {
	
	private static final int PULL_TO_REFLUSH = 0;
	private static final int RELEASE_REFLUSH = 1;
	private static final int REFLUSH = 2;
	private View view;
	private int mCurrentState = 0;
	private RotateAnimation rotateUpAnimation;
	private RotateAnimation rotateDownAnimation;
	private ImageView ivPullArraw;
	private ProgressBar pbReflush;
	private TextView tvReflushTitle;
	private TextView tvReflushTime;
	private int padding;

	public PullToReflushListView(Context context) {
		super(context);
		init();
	}

	public PullToReflushListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PullToReflushListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		initHeader();
		initFooter();
	}

	private void initFooter() {
	}

	private void initHeader() {
		view = View.inflate(getContext(), R.layout.header_list_view, null);
		ivPullArraw = (ImageView) view.findViewById(R.id.iv_pull_arraw);
		pbReflush = (ProgressBar) view.findViewById(R.id.pb_reflush);
		tvReflushTitle = (TextView) view.findViewById(R.id.tv_reflush_title);
		tvReflushTime = (TextView) view.findViewById(R.id.tv_reflush_time);
		addHeaderView(view);
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
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(mCurrentState == REFLUSH){
			return super.onTouchEvent(ev);
		}
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
			if(dY > 0 && getFirstVisiblePosition() == 0){
				int newPadding = (int) (padding + dY);
				if(newPadding >= 0 && mCurrentState != RELEASE_REFLUSH){
					mCurrentState  = RELEASE_REFLUSH;
					updateState();
				} else if(newPadding <0 && mCurrentState != PULL_TO_REFLUSH) {
					mCurrentState  = PULL_TO_REFLUSH;
					updateState();
				}
				view.setPadding(0, newPadding, 0, 0);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if(mCurrentState == PULL_TO_REFLUSH){
				view.setPadding(0, padding, 0, 0);
			}else {
				onUpdateStateListener.onUpdateState();
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
		return super.onTouchEvent(ev);
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

	private String getCurrentTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(new Date());
		return currentTime;
	}
}
