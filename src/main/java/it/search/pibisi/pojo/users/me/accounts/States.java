
package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class States {

	@JsonProperty("active")
	private Boolean active;
	@JsonProperty("alerts")
	private Alerts alerts;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Alerts getAlerts() {
		return alerts;
	}

	public void setAlerts(Alerts alerts) {
		this.alerts = alerts;
	}

}
