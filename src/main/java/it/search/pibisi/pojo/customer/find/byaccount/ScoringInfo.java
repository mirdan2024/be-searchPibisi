package it.search.pibisi.pojo.customer.find.byaccount;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoringInfo {

	@JsonProperty("pep")
	private List<String> pep;
	@JsonProperty("sanctioned")
	private List<String> sanctioned;
	@JsonProperty("terrorist")
	private List<String> terrorist;
	@JsonProperty("media")
	private List<String> media;
	@JsonProperty("adverse_info")
	private List<String> adverseInfo;
	@JsonProperty("high_risk")
	private List<String> highRisk;
	@JsonProperty("matches")
	private List<String> matches;

	public List<String> getPep() {
		return pep;
	}

	public void setPep(List<String> pep) {
		this.pep = pep;
	}

	public List<String> getSanctioned() {
		return sanctioned;
	}

	public void setSanctioned(List<String> sanctioned) {
		this.sanctioned = sanctioned;
	}

	public List<String> getTerrorist() {
		return terrorist;
	}

	public void setTerrorist(List<String> terrorist) {
		this.terrorist = terrorist;
	}

	public List<String> getMedia() {
		return media;
	}

	public void setMedia(List<String> media) {
		this.media = media;
	}

	public List<String> getAdverseInfo() {
		return adverseInfo;
	}

	public void setAdverseInfo(List<String> adverseInfo) {
		this.adverseInfo = adverseInfo;
	}

	public List<String> getHighRisk() {
		return highRisk;
	}

	public void setHighRisk(List<String> highRisk) {
		this.highRisk = highRisk;
	}

	public List<String> getMatches() {
		return matches;
	}

	public void setMatches(List<String> matches) {
		this.matches = matches;
	}

}
