
package it.search.pibisi.pojo.customers.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Soi {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("status")
	private String status;
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("internal_uri")
	private String internalUri;
	@JsonProperty("version")
	private String version;
	@JsonProperty("reliability")
	private Double reliability;
	@JsonProperty("gathered_at")
	private String gatheredAt;
	@JsonProperty("published_at")
	private Object publishedAt;
	@JsonProperty("active")
	private Boolean active;
	@JsonProperty("has_screenshot")
	private Boolean hasScreenshot;
	@JsonProperty("has_content_body")
	private Boolean hasContentBody;
	@JsonProperty("group_uuid")
	private String groupUuid;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("description")
	private String description;
	@JsonProperty("soi_group")
	private SoiGroup soiGroup;
	@JsonProperty("rel_date")
	private String relDate;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getInternalUri() {
		return internalUri;
	}

	public void setInternalUri(String internalUri) {
		this.internalUri = internalUri;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Double getReliability() {
		return reliability;
	}

	public void setReliability(Double reliability) {
		this.reliability = reliability;
	}

	public String getGatheredAt() {
		return gatheredAt;
	}

	public void setGatheredAt(String gatheredAt) {
		this.gatheredAt = gatheredAt;
	}

	public Object getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Object publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getHasScreenshot() {
		return hasScreenshot;
	}

	public void setHasScreenshot(Boolean hasScreenshot) {
		this.hasScreenshot = hasScreenshot;
	}

	public Boolean getHasContentBody() {
		return hasContentBody;
	}

	public void setHasContentBody(Boolean hasContentBody) {
		this.hasContentBody = hasContentBody;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SoiGroup getSoiGroup() {
		return soiGroup;
	}

	public void setSoiGroup(SoiGroup soiGroup) {
		this.soiGroup = soiGroup;
	}

	public String getRelDate() {
		return relDate;
	}

	public void setRelDate(String relDate) {
		this.relDate = relDate;
	}

}
