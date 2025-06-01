package it.search.pibisi.pojo.customer.find.bycustomer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Soi {
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("active")
	private boolean active;
	@JsonProperty("status")
	private String status;
	@JsonProperty("version")
	private String version;
	@JsonProperty("rel_date")
	private String relDate;
	@JsonProperty("soi_group")
	private SoiGroup soiGroup;
	@JsonProperty("group_uuid")
	private String groupUuid;
	@JsonProperty("description")
	private String description;
	@JsonProperty("gathered_at")
	private String gatheredAt;
	@JsonProperty("reliability")
	private double reliability;
	@JsonProperty("internal_uri")
	private String internalUri;
	@JsonProperty("published_at")
	private String publishedAt;
	@JsonProperty("has_screenshot")
	private boolean hasScreenshot;
	@JsonProperty("has_content_body")
	private boolean hasContentBody;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRelDate() {
		return relDate;
	}

	public void setRelDate(String relDate) {
		this.relDate = relDate;
	}

	public SoiGroup getSoiGroup() {
		return soiGroup;
	}

	public void setSoiGroup(SoiGroup soiGroup) {
		this.soiGroup = soiGroup;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGatheredAt() {
		return gatheredAt;
	}

	public void setGatheredAt(String gatheredAt) {
		this.gatheredAt = gatheredAt;
	}

	public double getReliability() {
		return reliability;
	}

	public void setReliability(double reliability) {
		this.reliability = reliability;
	}

	public String getInternalUri() {
		return internalUri;
	}

	public void setInternalUri(String internalUri) {
		this.internalUri = internalUri;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public boolean isHasScreenshot() {
		return hasScreenshot;
	}

	public void setHasScreenshot(boolean hasScreenshot) {
		this.hasScreenshot = hasScreenshot;
	}

	public boolean isHasContentBody() {
		return hasContentBody;
	}

	public void setHasContentBody(boolean hasContentBody) {
		this.hasContentBody = hasContentBody;
	}

}
