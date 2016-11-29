package com.zhaowenbin.wisdombj.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CarouseViewPager extends ViewPager {

	public CarouseViewPager(Context context) {
		super(context);
	}
	
	public CarouseViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	float downX;
	float downY;
	@Override
	public boolean dispatchTouchEvent(MotionEvent arg0) {
		getParent().requestDisallowInterceptTouchEvent(true);
		switch (arg0.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = arg0.getX();
			downY = arg0.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = arg0.getX();
			float moveY = arg0.getY();
			float dX = moveX - downX;
			float dY = moveY - downY;
			if(Math.abs(dX) > Math.abs(dY)){
				if(dX > 5){
					if(getCurrentItem() == 0){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else if(dX < -5){
					if(getCurrentItem() == getAdapter().getCount() -1){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}	
			}else if(Math.abs(dX) < Math.abs(dY) && Math.abs(dY) > 5){
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP:
			
			break;			
		default:
			break;
		}
		return super.dispatchTouchEvent(arg0);
	}
}
