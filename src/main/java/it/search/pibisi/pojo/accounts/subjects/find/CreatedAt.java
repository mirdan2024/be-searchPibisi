package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatedAt {

	@JsonProperty("date")
	private String date;
	@JsonProperty("timezone_type")
	private Integer timezoneType;
	@JsonProperty("timezone")
	private String timezone;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTimezoneType() {
		return timezoneType;
	}

	public void setTimezoneType(Integer timezoneType) {
		this.timezoneType = timezoneType;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}
