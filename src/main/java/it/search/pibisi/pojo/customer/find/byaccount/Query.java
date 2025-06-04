package it.search.pibisi.pojo.customer.find.byaccount;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {

	@JsonProperty("status")
	private String status;
	@JsonProperty("risk")
	private String risk;
	@JsonProperty("group")
	private String group;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
