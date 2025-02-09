package it.search.pibisi.pojo.accounts.subjects.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {

	@JsonProperty("pois")
	private List<Poi__1> pois;
	@JsonProperty("threshold")
	private Double threshold;
	@JsonProperty("match_config")
	private MatchConfig matchConfig;

	public List<Poi__1> getPois() {
		return pois;
	}

	public void setPois(List<Poi__1> pois) {
		this.pois = pois;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public MatchConfig getMatchConfig() {
		return matchConfig;
	}

	public void setMatchConfig(MatchConfig matchConfig) {
		this.matchConfig = matchConfig;
	}

}
