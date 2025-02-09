package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscardRuleDefinition {

	@JsonProperty("name")
	private String name;
	@JsonProperty("params")
	private Params params;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Params getParams() {
		return params;
	}

	public void setParams(Params params) {
		this.params = params;
	}

}
