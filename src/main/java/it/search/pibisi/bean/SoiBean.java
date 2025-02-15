package it.search.pibisi.bean;

import java.time.OffsetDateTime;

public class SoiBean {

	private String uuid;
	private String status;
	private String uri;
	private String internalUri;
	private String version;
	private Integer reliability;
	private OffsetDateTime gatheredAt;
	private OffsetDateTime publishedAt;
	private Boolean active;
	private Boolean hasScreenshot;
	private Boolean hasContentBody;
	private String groupUuid;
	private String scope;
	private String description;
	private SoiGroupBean soiGroup;
	private String pibisiUri;
	private OffsetDateTime relDate;

	// Getter e Setter
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

	public Integer getReliability() {
		return reliability;
	}

	public void setReliability(Integer reliability) {
		this.reliability = reliability;
	}

	public OffsetDateTime getGatheredAt() {
		return gatheredAt;
	}

	public void setGatheredAt(OffsetDateTime gatheredAt) {
		this.gatheredAt = gatheredAt;
	}

	public OffsetDateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(OffsetDateTime publishedAt) {
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

	public SoiGroupBean getSoiGroup() {
		return soiGroup;
	}

	public void setSoiGroup(SoiGroupBean soiGroup) {
		this.soiGroup = soiGroup;
	}

	public String getPibisiUri() {
		return pibisiUri;
	}

	public void setPibisiUri(String pibisiUri) {
		this.pibisiUri = pibisiUri;
	}

	public OffsetDateTime getRelDate() {
		return relDate;
	}

	public void setRelDate(OffsetDateTime relDate) {
		this.relDate = relDate;
	}
}
