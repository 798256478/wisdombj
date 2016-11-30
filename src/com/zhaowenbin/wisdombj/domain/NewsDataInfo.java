package com.zhaowenbin.wisdombj.domain;

import java.util.ArrayList;

import android.R.integer;

public class NewsDataInfo {
	public int retcode;
	public ArrayList<Integer> extend;
	public ArrayList<NewsMenuData> data;
	public class NewsMenuData {
		public ArrayList<NewsChildrenData> children;
		public int id;
		public String title;
		public int type;
		public String url;
		@Override
		public String toString() {
			return "data [children=" + children + ", id=" + id + ", title="
					+ title + ", type=" + type + "]";
		}
		
	}
	
	public class NewsChildrenData {
		public int id;
		public String title;
		public int type;
		public String url;
		@Override
		public String toString() {
			return "NewsChildrenData [id=" + id + ", title=" + title
					+ ", type=" + type + ", url=" + url + "]";
		}
		
	}

	@Override
	public String toString() {
		return "NewsDataInfo [retcode=" + retcode + ", extend=" + extend
				+ ", data=" + data + "]";
	}

}
