package it.search.pibisi.pojo.customer.find.bycustomer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoiGroup {
	@JsonProperty("info")
	private String info;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("title")
	private String title;
	@JsonProperty("issuer")
	private String issuer;
	@JsonProperty("description")
	private String description;
	@JsonProperty("group_set_code")
	private String groupSetCode;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupSetCode() {
		return groupSetCode;
	}

	public void setGroupSetCode(String groupSetCode) {
		this.groupSetCode = groupSetCode;
	}

}
