
package it.search.pibisi.pojo.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flags {

	@JsonProperty("has_adverse_info")
	private Boolean hasAdverseInfo;
	@JsonProperty("has_matches")
	private Boolean hasMatches;
	@JsonProperty("has_media")
	private Boolean hasMedia;
	@JsonProperty("has_media_date")
	private String hasMediaDate;
	@JsonProperty("is_high_risk")
	private Boolean isHighRisk;
	@JsonProperty("is_pep")
	private Boolean isPep;
	@JsonProperty("was_pep")
	private Boolean wasPep;
	@JsonProperty("was_pep_date")
	private String wasPepDate;
	@JsonProperty("is_sanctioned")
	private Boolean isSanctioned;
	@JsonProperty("was_sanctioned")
	private Boolean wasSanctioned;
	@JsonProperty("was_sanctioned_date")
	private String wasSanctionedDate;
	@JsonProperty("is_terrorist")
	private Boolean isTerrorist;

	public Boolean getHasAdverseInfo() {
		return hasAdverseInfo;
	}

	public void setHasAdverseInfo(Boolean hasAdverseInfo) {
		this.hasAdverseInfo = hasAdverseInfo;
	}

	public Boolean getHasMatches() {
		return hasMatches;
	}

	public void setHasMatches(Boolean hasMatches) {
		this.hasMatches = hasMatches;
	}

	public Boolean getHasMedia() {
		return hasMedia;
	}

	public void setHasMedia(Boolean hasMedia) {
		this.hasMedia = hasMedia;
	}

	public String getHasMediaDate() {
		return hasMediaDate;
	}

	public void setHasMediaDate(String hasMediaDate) {
		this.hasMediaDate = hasMediaDate;
	}

	public Boolean getIsHighRisk() {
		return isHighRisk;
	}

	public void setIsHighRisk(Boolean isHighRisk) {
		this.isHighRisk = isHighRisk;
	}

	public Boolean getIsPep() {
		return isPep;
	}

	public void setIsPep(Boolean isPep) {
		this.isPep = isPep;
	}

	public Boolean getWasPep() {
		return wasPep;
	}

	public void setWasPep(Boolean wasPep) {
		this.wasPep = wasPep;
	}

	public String getWasPepDate() {
		return wasPepDate;
	}

	public void setWasPepDate(String wasPepDate) {
		this.wasPepDate = wasPepDate;
	}

	public Boolean getIsSanctioned() {
		return isSanctioned;
	}

	public void setIsSanctioned(Boolean isSanctioned) {
		this.isSanctioned = isSanctioned;
	}

	public Boolean getWasSanctioned() {
		return wasSanctioned;
	}

	public void setWasSanctioned(Boolean wasSanctioned) {
		this.wasSanctioned = wasSanctioned;
	}

	public String getWasSanctionedDate() {
		return wasSanctionedDate;
	}

	public void setWasSanctionedDate(String wasSanctionedDate) {
		this.wasSanctionedDate = wasSanctionedDate;
	}

	public Boolean getIsTerrorist() {
		return isTerrorist;
	}

	public void setIsTerrorist(Boolean isTerrorist) {
		this.isTerrorist = isTerrorist;
	}

}
