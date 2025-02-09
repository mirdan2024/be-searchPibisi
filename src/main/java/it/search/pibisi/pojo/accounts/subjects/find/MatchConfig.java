package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchConfig {

	@JsonProperty("default")
	private Default__1 _default;

	public Default__1 getDefault() {
		return _default;
	}

	public void setDefault(Default__1 _default) {
		this._default = _default;
	}

}
