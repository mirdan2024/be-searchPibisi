package it.search.pibisi.pojo.customer.find.bycustomer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flags {
	@JsonProperty("is_pep")
	private boolean isPep;
	@JsonProperty("was_pep")
	private boolean wasPep;
	@JsonProperty("was_pep_date")
	private String wasPepDate;
	@JsonProperty("is_sanctioned")
	private boolean isSanctioned;
	@JsonProperty("was_sanctioned")
	private boolean wasSanctioned;
	@JsonProperty("was_sanctioned_date")
	private String wasSanctionedDate;
	@JsonProperty("is_terrorist")
	private boolean isTerrorist;
	@JsonProperty("has_media")
	private boolean hasMedia;
	@JsonProperty("has_media_date")
	private String hasMediaDate;
	@JsonProperty("has_adverse_info")
	private boolean hasAdverseInfo;
	@JsonProperty("is_high_risk")
	private boolean isHighRisk;
	@JsonProperty("has_matches")
	private boolean hasMatches;

	public boolean isPep() {
		return isPep;
	}

	public void setPep(boolean isPep) {
		this.isPep = isPep;
	}

	public boolean isWasPep() {
		return wasPep;
	}

	public void setWasPep(boolean wasPep) {
		this.wasPep = wasPep;
	}

	public String getWasPepDate() {
		return wasPepDate;
	}

	public void setWasPepDate(String wasPepDate) {
		this.wasPepDate = wasPepDate;
	}

	public boolean isSanctioned() {
		return isSanctioned;
	}

	public void setSanctioned(boolean isSanctioned) {
		this.isSanctioned = isSanctioned;
	}

	public boolean isWasSanctioned() {
		return wasSanctioned;
	}

	public void setWasSanctioned(boolean wasSanctioned) {
		this.wasSanctioned = wasSanctioned;
	}

	public String getWasSanctionedDate() {
		return wasSanctionedDate;
	}

	public void setWasSanctionedDate(String wasSanctionedDate) {
		this.wasSanctionedDate = wasSanctionedDate;
	}

	public boolean isTerrorist() {
		return isTerrorist;
	}

	public void setTerrorist(boolean isTerrorist) {
		this.isTerrorist = isTerrorist;
	}

	public boolean isHasMedia() {
		return hasMedia;
	}

	public void setHasMedia(boolean hasMedia) {
		this.hasMedia = hasMedia;
	}

	public String getHasMediaDate() {
		return hasMediaDate;
	}

	public void setHasMediaDate(String hasMediaDate) {
		this.hasMediaDate = hasMediaDate;
	}

	public boolean isHasAdverseInfo() {
		return hasAdverseInfo;
	}

	public void setHasAdverseInfo(boolean hasAdverseInfo) {
		this.hasAdverseInfo = hasAdverseInfo;
	}

	public boolean isHighRisk() {
		return isHighRisk;
	}

	public void setHighRisk(boolean isHighRisk) {
		this.isHighRisk = isHighRisk;
	}

	public boolean isHasMatches() {
		return hasMatches;
	}

	public void setHasMatches(boolean hasMatches) {
		this.hasMatches = hasMatches;
	}

}
