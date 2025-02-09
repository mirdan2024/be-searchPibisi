package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Params {

	@JsonProperty("value")
	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
