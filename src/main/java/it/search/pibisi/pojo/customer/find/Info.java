
package it.search.pibisi.pojo.customer.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Info {

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
	private List<Object> highRisk;
	@JsonProperty("matches")
	private List<String> matches;

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

	public List<Object> getHighRisk() {
		return highRisk;
	}

	public void setHighRisk(List<Object> highRisk) {
		this.highRisk = highRisk;
	}

	public List<String> getMatches() {
		return matches;
	}

	public void setMatches(List<String> matches) {
		this.matches = matches;
	}

}
