
package it.search.pibisi.pojo.customer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("status")
	private String status;
	@JsonProperty("risk")
	private String risk;
	@JsonProperty("group")
	private Object group;
	@JsonProperty("expires_at")
	private String expiresAt;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("removed_at")
	private String removedAt;
	@JsonProperty("errors")
	private List<Object> errors;
	@JsonProperty("scoring")
	private Scoring scoring;
	@JsonProperty("account_uuid")
	private String accountUuid;
	@JsonProperty("docs")
	private List<Object> docs;
	@JsonProperty("info")
	private List<Info> info;

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

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public Object getGroup() {
		return group;
	}

	public void setGroup(Object group) {
		this.group = group;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getRemovedAt() {
		return removedAt;
	}

	public void setRemovedAt(String removedAt) {
		this.removedAt = removedAt;
	}

	public List<Object> getErrors() {
		return errors;
	}

	public void setErrors(List<Object> errors) {
		this.errors = errors;
	}

	public Scoring getScoring() {
		return scoring;
	}

	public void setScoring(Scoring scoring) {
		this.scoring = scoring;
	}

	public String getAccountUuid() {
		return accountUuid;
	}

	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	public List<Object> getDocs() {
		return docs;
	}

	public void setDocs(List<Object> docs) {
		this.docs = docs;
	}

	public List<Info> getInfo() {
		return info;
	}

	public void setInfo(List<Info> info) {
		this.info = info;
	}

}
