package it.search.pibisi.pojo.accounts.subjects.find;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("info")
	private List<Info__2> info;
	@JsonProperty("scoring")
	private Scoring__1 scoring;
	@JsonProperty("created_at")
	private String createdAt;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Info__2> getInfo() {
		return info;
	}

	public void setInfo(List<Info__2> info) {
		this.info = info;
	}

	public Scoring__1 getScoring() {
		return scoring;
	}

	public void setScoring(Scoring__1 scoring) {
		this.scoring = scoring;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


}
