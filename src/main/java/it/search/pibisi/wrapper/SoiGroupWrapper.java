package it.search.pibisi.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoiGroupWrapper {
	@JsonProperty("group_set_code")
	private String groupSetCode;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("title")
	private String title;

	@JsonProperty("issuer")
	private String issuer;

	@JsonProperty("uuid")
	private String uuid;

	@JsonProperty("description")
	private String description;

	@JsonProperty("info")
	private String info;


	// Getter e Setter
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getGroupSetCode() {
		return groupSetCode;
	}

	public void setGroupSetCode(String groupSetCode) {
		this.groupSetCode = groupSetCode;
	}
}
