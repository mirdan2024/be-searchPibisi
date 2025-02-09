package it.search.pibisi.bean;

import java.util.HashMap;
import java.util.List;

public class MatchListBean {

	private List<NameFullBean> nameFull;
	private HashMap<String, InfoBean> infoMap; 
	private List<NewsBean> news;

	public HashMap<String, InfoBean> getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(HashMap<String, InfoBean> infoMap) {
		this.infoMap = infoMap;
	}

	public List<NameFullBean> getNameFull() {
		return nameFull;
	}

	public void setNameFull(List<NameFullBean> nameFull) {
		this.nameFull = nameFull;
	}

	public List<NewsBean> getNews() {
		return news;
	}

	public void setNews(List<NewsBean> news) {
		this.news = news;
	}

}
