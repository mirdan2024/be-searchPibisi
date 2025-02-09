package it.search.pibisi.pojo.accounts.subjects.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResult {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("performed_at")
	private String performedAt;
	@JsonProperty("threshold")
	private Double threshold;
	@JsonProperty("status")
	private String status;
	@JsonProperty("pois")
	private List<Poi> pois;
	@JsonProperty("matches")
	private List<Match> matches;
	@JsonProperty("discarded_matches")
	private List<Object> discardedMatches;
	@JsonProperty("account_uuid")
	private String accountUuid;
	@JsonProperty("name_comparison_configs")
	private NameComparisonConfigs nameComparisonConfigs;
	@JsonProperty("full_matches")
	private List<Object> fullMatches;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPerformedAt() {
		return performedAt;
	}

	public void setPerformedAt(String performedAt) {
		this.performedAt = performedAt;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Poi> getPois() {
		return pois;
	}

	public void setPois(List<Poi> pois) {
		this.pois = pois;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public List<Object> getDiscardedMatches() {
		return discardedMatches;
	}

	public void setDiscardedMatches(List<Object> discardedMatches) {
		this.discardedMatches = discardedMatches;
	}

	public String getAccountUuid() {
		return accountUuid;
	}

	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	public NameComparisonConfigs getNameComparisonConfigs() {
		return nameComparisonConfigs;
	}

	public void setNameComparisonConfigs(NameComparisonConfigs nameComparisonConfigs) {
		this.nameComparisonConfigs = nameComparisonConfigs;
	}

	public List<Object> getFullMatches() {
		return fullMatches;
	}

	public void setFullMatches(List<Object> fullMatches) {
		this.fullMatches = fullMatches;
	}

}
