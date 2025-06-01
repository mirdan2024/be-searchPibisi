package it.search.pibisi.pojo.customer.find.bycustomer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoScoring {
	@JsonProperty("pep")
	private List<Object> pep;
	@JsonProperty("sanctioned")
	private List<Object> sanctioned;
	@JsonProperty("terrorist")
	private List<Object> terrorist;
	@JsonProperty("media")
	private List<Object> media;
	@JsonProperty("adverse_info")
	private List<Object> adverseInfo;
	@JsonProperty("high_risk")
	private List<Object> highisk;
	@JsonProperty("matches")
	private List<Object> matches;

	public List<Object> getPep() {
		return pep;
	}

	public void setPep(List<Object> pep) {
		this.pep = pep;
	}

	public List<Object> getSanctioned() {
		return sanctioned;
	}

	public void setSanctioned(List<Object> sanctioned) {
		this.sanctioned = sanctioned;
	}

	public List<Object> getTerrorist() {
		return terrorist;
	}

	public void setTerrorist(List<Object> terrorist) {
		this.terrorist = terrorist;
	}

	public List<Object> getMedia() {
		return media;
	}

	public void setMedia(List<Object> media) {
		this.media = media;
	}

	public List<Object> getAdverseInfo() {
		return adverseInfo;
	}

	public void setAdverseInfo(List<Object> adverseInfo) {
		this.adverseInfo = adverseInfo;
	}

	public List<Object> getHighisk() {
		return highisk;
	}

	public void setHighisk(List<Object> highisk) {
		this.highisk = highisk;
	}

	public List<Object> getMatches() {
		return matches;
	}

	public void setMatches(List<Object> matches) {
		this.matches = matches;
	}

}
