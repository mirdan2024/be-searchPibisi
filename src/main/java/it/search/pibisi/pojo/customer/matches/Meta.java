package it.search.pibisi.pojo.customer.matches;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

	@JsonProperty("count")
	private int count;
	@JsonProperty("version")
	private String version;
	@JsonProperty("time")
	private String time;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

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
