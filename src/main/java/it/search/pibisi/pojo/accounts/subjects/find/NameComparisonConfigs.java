package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NameComparisonConfigs {

	@JsonProperty("default")
	private Default _default;

	public Default getDefault() {
		return _default;
	}

	public void setDefault(Default _default) {
		this._default = _default;
	}

}
