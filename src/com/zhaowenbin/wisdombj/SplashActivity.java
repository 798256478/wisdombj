package com.zhaowenbin.wisdombj;

import com.zhaowenbin.wisdombj.utils.ConstantValueUtil;
import com.zhaowenbin.wisdombj.utils.SpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

public class SplashActivity extends Activity {

	private View mIvSplashImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		initView();
		initAnimation();
	}

	private void initAnimation() {
		//��ת����
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(1000);
		rotateAnimation.setFillAfter(true);
		//���Ŷ���
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(1000);
		scaleAnimation.setFillAfter(true);
		//���䶯��
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setFillAfter(true);
		//��������
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(rotateAnimation);
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);
		//��Ӷ���
		mIvSplashImg.startAnimation(animationSet);
		//����״̬����
		animationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Boolean isFirstInter = SpUtil.getBoolean(getApplicationContext(), ConstantValueUtil.FIRST_INTER, true);
				if(isFirstInter){
					startActivity(new Intent(getApplicationContext(), GuideActivity.class));
				} else {
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
				}
				finish();
			}
		});
	}

	private void initView() {
		mIvSplashImg = findViewById(R.id.iv_splash_img);
	}

}
