
package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchConfigDefinition {

	@JsonProperty("name")
	private String name;
	@JsonProperty("params")
	private Params__1 params;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Params__1 getParams() {
		return params;
	}

	public void setParams(Params__1 params) {
		this.params = params;
	}

}
