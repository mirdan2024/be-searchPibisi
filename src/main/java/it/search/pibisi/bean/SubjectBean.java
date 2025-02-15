package it.search.pibisi.bean;

import java.util.Date;
import java.util.List;

public class SubjectBean {

	private String uuid;

	private String createdAt;

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String string) {
		this.createdAt = string;
	}

	private List<SubjectInfoBean> subjectInfoBean;

	// Getter e Setter
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<SubjectInfoBean> getSubjectInfoBean() {
		return subjectInfoBean;
	}

	public void setSubjectInfoBean(List<SubjectInfoBean> subjectInfoBean) {
		this.subjectInfoBean = subjectInfoBean;
	}

}
