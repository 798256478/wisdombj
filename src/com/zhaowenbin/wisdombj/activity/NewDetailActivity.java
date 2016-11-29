package com.zhaowenbin.wisdombj.activity;

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
		wvNewDetail.loadUrl(url);
		settings = wvNewDetail.getSettings();
		settings.setJavaScriptEnabled(true);
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
				pbWebLoad.setVisibility(View.INVISIBLE);
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				wvNewDetail.loadUrl(url);
				return true;
			}
		});
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
			
			break;			
		default:
			break;
		}
	}
}
