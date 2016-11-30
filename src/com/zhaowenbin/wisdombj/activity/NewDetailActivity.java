package com.zhaowenbin.wisdombj.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.zhaowenbin.wisdombj.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class NewDetailActivity extends Activity implements OnClickListener{
	protected int mCurrentTextSize = 2;
	private WebView wvNewDetail;
	private String url;
	private WebSettings settings;
	private ProgressBar pbWebLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_detail);
		url = (String) getIntent().getExtras().get("url");
		initView();
	}

	private void initView() {
		wvNewDetail = (WebView) findViewById(R.id.wv_new_detail);
		ImageButton ibBack = (ImageButton) findViewById(R.id.ib_back);
		ImageButton ibTextSize = (ImageButton) findViewById(R.id.ib_text_size);
		ImageButton ibShare = (ImageButton) findViewById(R.id.ib_share);
		pbWebLoad = (ProgressBar) findViewById(R.id.pb_web_load);
		ibBack.setOnClickListener(this);
		ibTextSize.setOnClickListener(this);
		ibShare.setOnClickListener(this);
		wvNewDetail.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				Log.i("NewDetailActivity", newProgress +"");
				pbWebLoad.setProgress(newProgress);
			}
		});
		
		wvNewDetail.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				pbWebLoad.setVisibility(View.VISIBLE);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pbWebLoad.setVisibility(View.GONE);
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				wvNewDetail.loadUrl(url);
				return true;
			}
		});
		
		settings = wvNewDetail.getSettings();
		settings.setJavaScriptEnabled(true);
		wvNewDetail.loadUrl(url);
	}
	
	private void showSetTextSizeDialog() {
		Builder builder = new AlertDialog.Builder(NewDetailActivity.this);
		builder.setTitle("请选择字体大小");
		String[] textSize = new String[]{"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"}; 
		builder.setSingleChoiceItems(textSize, mCurrentTextSize, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mCurrentTextSize = which;
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (mCurrentTextSize) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					break;	
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					break;					
				default:
					break;
				}
			}
		});
		
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			finish();
			break;
		case R.id.ib_text_size:
			showSetTextSizeDialog();
			break;
		case R.id.ib_share:
			showShare();
			break;			
		default:
			break;
		}
	}
	
	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");
		 
		// 启动分享GUI
		 oks.show(this);
	}
}
