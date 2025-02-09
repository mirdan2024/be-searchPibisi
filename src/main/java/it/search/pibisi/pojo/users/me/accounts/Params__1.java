package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Params__1 {

	@JsonProperty("threshold")
	private Double threshold;
	@JsonProperty("max_allowed_blanks")
	private Integer maxAllowedBlanks;
	@JsonProperty("max_allowed_blanks_name")
	private Integer maxAllowedBlanksName;
	@JsonProperty("max_allowed_blanks_surname")
	private Integer maxAllowedBlanksSurname;
	@JsonProperty("allow_extra_words")
	private Boolean allowExtraWords;

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public Integer getMaxAllowedBlanks() {
		return maxAllowedBlanks;
	}

	public void setMaxAllowedBlanks(Integer maxAllowedBlanks) {
		this.maxAllowedBlanks = maxAllowedBlanks;
	}

	public Integer getMaxAllowedBlanksName() {
		return maxAllowedBlanksName;
	}

	public void setMaxAllowedBlanksName(Integer maxAllowedBlanksName) {
		this.maxAllowedBlanksName = maxAllowedBlanksName;
	}

	public Integer getMaxAllowedBlanksSurname() {
		return maxAllowedBlanksSurname;
	}

	public void setMaxAllowedBlanksSurname(Integer maxAllowedBlanksSurname) {
		this.maxAllowedBlanksSurname = maxAllowedBlanksSurname;
	}

	public Boolean getAllowExtraWords() {
		return allowExtraWords;
	}

	public void setAllowExtraWords(Boolean allowExtraWords) {
		this.allowExtraWords = allowExtraWords;
	}

}
