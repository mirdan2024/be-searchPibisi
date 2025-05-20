package it.search.pibisi.wrapper;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.common.pibisi.bean.SoiGroupBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoiWrapper {
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
	private Integer reliability;

	@JsonProperty("gathered_at")
	private Date gatheredAt;

	@JsonProperty("published_at")
	private Date publishedAt;

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
	private SoiGroupBean soiGroup;

	@JsonProperty("pibisi_uri")
	private String pibisiUri;

	@JsonProperty("rel_date")
	private Date relDate;

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

	public Date getGatheredAt() {
		return gatheredAt;
	}

	public void setGatheredAt(Date gatheredAt) {
		this.gatheredAt = gatheredAt;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
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

	public Date getRelDate() {
		return relDate;
	}

	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}
}
