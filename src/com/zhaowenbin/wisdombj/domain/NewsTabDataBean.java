package com.zhaowenbin.wisdombj.domain;

import java.util.ArrayList;

public class NewsTabDataBean {
	public int retcode;
	public Tabdata data;
	public class Tabdata{
		public String countcommenturl;
		public String more;
		public ArrayList<TabNewsData> news;
		public String title;
		public ArrayList<TabTopic> topic;
		public ArrayList<TabTopNews> topnews;
		@Override
		public String toString() {
			return "Tabdata [countcommenturl=" + countcommenturl + ", more="
					+ more + ", news=" + news + ", title=" + title + ", topic="
					+ topic + ", topnews=" + topnews + "]";
		}
		
	}
	
	public class TabNewsData{
		public Boolean comment;
		public String commentList;
		public String commenturl;
		public int id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "TabNewsData [comment=" + comment + ", commentList="
					+ commentList + ", commenturl=" + commenturl + ", id=" + id
					+ ", listimage=" + listimage + ", pubdate=" + pubdate
					+ ", title=" + title + ", type=" + type + ", url=" + url
					+ "]";
		}
		
	}
	
	public class TabTopic{
		public String description;
		public int id;
		public String listimage;
		public int sort;
		public String title;
		public String url;
		@Override
		public String toString() {
			return "TabTopic [description=" + description + ", id=" + id
					+ ", listimage=" + listimage + ", sort=" + sort
					+ ", title=" + title + ", url=" + url + "]";
		}
		
	}
	
	public class TabTopNews{
		public Boolean comment;
		public String commentList;
		public String commenturl;
		public int id;
		public String pubdate;
		public String title;
		public String topimage;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "TabTopNews [comment=" + comment + ", commentList="
					+ commentList + ", commenturl=" + commenturl + ", id=" + id
					+ ", pubdate=" + pubdate + ", title=" + title
					+ ", topimage=" + topimage + ", type=" + type + ", url="
					+ url + "]";
		}
		
	}

	@Override
	public String toString() {
		return "NewsTabDataBean [retcode=" + retcode + ", data=" + data + "]";
	}
	
}
