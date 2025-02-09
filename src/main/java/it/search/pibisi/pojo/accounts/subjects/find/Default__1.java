package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Default__1 {

	@JsonProperty("search_type")
	private String searchType;
	@JsonProperty("threshold")
	private String threshold;
	@JsonProperty("max_allowed_blanks")
	private Integer maxAllowedBlanks;
	@JsonProperty("max_allowed_blanks_name")
	private Integer maxAllowedBlanksName;
	@JsonProperty("max_allowed_blanks_surname")
	private Integer maxAllowedBlanksSurname;
	@JsonProperty("allow_extra_words")
	private Boolean allowExtraWords;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
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
