package it.search.pibisi.pojo.accounts.subjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

	@JsonProperty("version")
	private String version;
	
	@JsonProperty("time")
	private String time;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
