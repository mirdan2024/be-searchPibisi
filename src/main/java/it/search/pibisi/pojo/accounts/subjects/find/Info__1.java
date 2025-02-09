package it.search.pibisi.pojo.accounts.subjects.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Info__1 {

	@JsonProperty("pep")
	private List<Object> pep;
	@JsonProperty("pep_pois")
	private List<Object> pepPois;
	@JsonProperty("pep_relations")
	private List<Object> pepRelations;
	@JsonProperty("sanctioned")
	private List<Object> sanctioned;
	@JsonProperty("terrorist")
	private List<String> terrorist;
	@JsonProperty("media")
	private List<Object> media;
	@JsonProperty("adverse_info")
	private List<Object> adverseInfo;
	@JsonProperty("high_risk")
	private List<String> highRisk;

	public List<Object> getPep() {
		return pep;
	}

	public void setPep(List<Object> pep) {
		this.pep = pep;
	}

	public List<Object> getPepPois() {
		return pepPois;
	}

	public void setPepPois(List<Object> pepPois) {
		this.pepPois = pepPois;
	}

	public List<Object> getPepRelations() {
		return pepRelations;
	}

	public void setPepRelations(List<Object> pepRelations) {
		this.pepRelations = pepRelations;
	}

	public List<Object> getSanctioned() {
		return sanctioned;
	}

	public void setSanctioned(List<Object> sanctioned) {
		this.sanctioned = sanctioned;
	}

	public List<String> getTerrorist() {
		return terrorist;
	}

	public void setTerrorist(List<String> terrorist) {
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

	public List<String> getHighRisk() {
		return highRisk;
	}

	public void setHighRisk(List<String> highRisk) {
		this.highRisk = highRisk;
	}

}
