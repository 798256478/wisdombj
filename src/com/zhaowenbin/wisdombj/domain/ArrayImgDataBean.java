package com.zhaowenbin.wisdombj.domain;

import java.util.ArrayList;

public class ArrayImgDataBean {
	public ArrayImgData data;
	public int retcode;
	
	public class ArrayImgData {
		public String countcommenturl;
		public String more;
		public ArrayList<NewsImgData> news;
		public String title;
	}
	
	public class NewsImgData {
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String largeimage;
		public String listimage;
		public String pubdate;
		public String smallimage;
		public String title;
		public String type;
		public String url;
	}
}
