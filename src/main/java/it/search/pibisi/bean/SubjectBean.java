package it.search.pibisi.bean;

import java.util.HashMap;

public class SubjectBean {

	private String uuid;

	private String createdAt;

	private HashMap<String, SubjectInfoBean> subjectInfoMap;

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String string) {
		this.createdAt = string;
	}

	// Getter e Setter
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public HashMap<String, SubjectInfoBean> getSubjectInfoMap() {
		return subjectInfoMap;
	}

	public void setSubjectInfoMap(HashMap<String, SubjectInfoBean> subjectInfoMap) {
		this.subjectInfoMap = subjectInfoMap;
	}

}
