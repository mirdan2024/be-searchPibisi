package it.search.pibisi.pojo.customer.find.bycustomer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Info {
	@JsonProperty("sois")
	private List<Soi> sois;
	@JsonProperty("type")
	private String type;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("group")
	private String group;
	@JsonProperty("content")
	private String content;

	public List<Soi> getSois() {
		return sois;
	}

	public void setSois(List<Soi> sois) {
		this.sois = sois;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
