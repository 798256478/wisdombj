package com.zhaowenbin.wisdombj.activity;

import java.util.ArrayList;
import java.util.List;

import com.zhaowenbin.wisdombj.R;
import com.zhaowenbin.wisdombj.adapter.GuideAdapter;
import com.zhaowenbin.wisdombj.utils.ConstantUtil;
import com.zhaowenbin.wisdombj.utils.SpUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GuideActivity extends Activity implements OnClickListener{
	private ViewPager mVpGuideStep;
	private Button mBtnEnterMain;
	private LinearLayout mLlGuidePoint;
	private List<ImageView> mImageViews;
	private ImageView mIvRedPoint;
	private int pointDx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		initView();
		initData();
		initAdapter();
	}

	private void initAdapter() {
		GuideAdapter guideAdapter = new GuideAdapter(mImageViews);
		mVpGuideStep.setAdapter(guideAdapter);
		mVpGuideStep.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 < mImageViews.size() - 1){
					mBtnEnterMain.setVisibility(View.INVISIBLE);
				} else {
					mBtnEnterMain.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.leftMargin = (int) (((float)pointDx) * arg1) + arg0 * pointDx;
				mIvRedPoint.setLayoutParams(params);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initData() {
		int[] guideImgIdArrays = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
		ImageView imageView = null;
		ImageView pointView = null;
		mImageViews = new ArrayList<ImageView>();
		for (int i = 0; i < guideImgIdArrays.length; i++) {
			imageView = new ImageView(this);
			imageView.setBackgroundResource(guideImgIdArrays[i]);
			mImageViews.add(imageView);
			
			pointView = new ImageView(this);
			pointView.setImageResource(R.drawable.shape_guide_point);
			android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.leftMargin = 15;
			if(i > 0){
				pointView.setLayoutParams(params);
			}
			mLlGuidePoint.addView(pointView);
		}
		
		mIvRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				mIvRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				
				pointDx = mLlGuidePoint.getChildAt(1).getLeft() -  mLlGuidePoint.getChildAt(0).getLeft();
			}
		});
	}

	private void initView() {
		mVpGuideStep = (ViewPager) findViewById(R.id.vp_guide_step);
		mBtnEnterMain = (Button) findViewById(R.id.btn_enter_main);
		mLlGuidePoint = (LinearLayout) findViewById(R.id.ll_guide_point);
		mIvRedPoint = (ImageView) findViewById(R.id.iv_red_point);
		
		mBtnEnterMain.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SpUtil.putBoolean(this, ConstantUtil.FIRST_INTER, false);
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
	
}
