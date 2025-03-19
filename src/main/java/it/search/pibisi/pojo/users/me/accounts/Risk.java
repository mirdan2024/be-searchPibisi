package it.search.pibisi.pojo.users.me.accounts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Risk {


	@JsonProperty("auto")
	private Boolean auto;
	@JsonProperty("thresholds")
	private Thresholds thresholds;
	@JsonProperty("alerts")
	private List<String> alerts;
	@JsonProperty("default")
	private String _default;

	public Boolean getAuto() {
		return auto;
	}

	public void setAuto(Boolean auto) {
		this.auto = auto;
	}

	public Thresholds getThresholds() {
		return thresholds;
	}

	public void setThresholds(Thresholds thresholds) {
		this.thresholds = thresholds;
	}

	public List<String> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<String> alerts) {
		this.alerts = alerts;
	}

	public String getDefault() {
		return _default;
	}

	public void setDefault(String _default) {
		this._default = _default;
	}

}
