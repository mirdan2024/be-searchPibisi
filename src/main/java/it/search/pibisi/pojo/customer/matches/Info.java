package it.search.pibisi.pojo.customer.matches;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Info {

	@JsonProperty("group")
	private Object group; // può essere Integer o null
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("type")
	private String type;
	@JsonProperty("content")
	private Object content; // può essere String o ContentObj (vedi sotto)
	@JsonProperty("sois")
	private List<Soi> sois;

	public Object getGroup() {
		return group;
	}

	public void setGroup(Object group) {
		this.group = group;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public List<Soi> getSois() {
		return sois;
	}

	public void setSois(List<Soi> sois) {
		this.sois = sois;
	}

}
