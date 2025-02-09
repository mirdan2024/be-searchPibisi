package it.search.pibisi.bean;

import java.util.List;

public class MatchListBean {

	private List<NameFullBean> nameFull;
	private List<InfoBean> info;
	private List<NewsBean> news;

	public List<InfoBean> getInfo() {
		return info;
	}

	public void setInfo(List<InfoBean> info) {
		this.info = info;
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
