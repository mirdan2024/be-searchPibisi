package it.search.pibisi.bean;

import java.util.List;

public class SubjectPoiBean {

	private Object content;

	private String group;

	private String uuid;

	private String type;

	private List<SoiBean> soiBean;

	// Getter e Setter
	public String getGroup() {
		return group;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getUuid() {
		return uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SoiBean> getSoiBean() {
		return soiBean;
	}

	public void setSoiBean(List<SoiBean> soiBean) {
		this.soiBean = soiBean;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}