package it.search.pibisi.pojo.accounts.subjects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("info")
	private List<Info> info;
	@JsonProperty("scoring")
	private Scoring scoring;
	@JsonProperty("created_at")
	private CreatedAt createdAt;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Info> getInfo() {
		return info;
	}

	public void setInfo(List<Info> info) {
		this.info = info;
	}

	public Scoring getScoring() {
		return scoring;
	}

	public void setScoring(Scoring scoring) {
		this.scoring = scoring;
	}

	public CreatedAt getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(CreatedAt createdAt) {
		this.createdAt = createdAt;
	}

}
