package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonAnalytics {

	@JsonProperty("rates")
	private Integer rates;
	@JsonProperty("matches")
	private Boolean matches;
	@JsonProperty("risk")
	private Risk risk;
	@JsonProperty("states")
	private States states;

	public Integer getRates() {
		return rates;
	}

	public void setRates(Integer rates) {
		this.rates = rates;
	}

	public Boolean getMatches() {
		return matches;
	}

	public void setMatches(Boolean matches) {
		this.matches = matches;
	}

	public Risk getRisk() {
		return risk;
	}

	public void setRisk(Risk risk) {
		this.risk = risk;
	}

	public States getStates() {
		return states;
	}

	public void setStates(States states) {
		this.states = states;
	}

}
