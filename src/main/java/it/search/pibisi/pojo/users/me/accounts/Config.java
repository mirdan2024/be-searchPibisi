package it.search.pibisi.pojo.users.me.accounts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {

	@JsonProperty("alias")
	private String alias;
	@JsonProperty("company_name")
	private String companyName;
	@JsonProperty("invoice")
	private List<Object> invoice;
	@JsonProperty("comm_emails")
	private List<String> commEmails;
	@JsonProperty("comm_callback")
	private String commCallback;
	@JsonProperty("person_analytics")
	private PersonAnalytics personAnalytics;
	@JsonProperty("behaviour_analytics")
	private List<Object> behaviourAnalytics;
	@JsonProperty("scoring_definitions")
	private List<ScoringDefinition> scoringDefinitions;
	@JsonProperty("poi_definitions")
	private List<Object> poiDefinitions;
	@JsonProperty("form_definitions")
	private List<Object> formDefinitions;
	@JsonProperty("customer_group_definitions")
	private List<Object> customerGroupDefinitions;
	@JsonProperty("customer_definitions")
	private List<Object> customerDefinitions;
	@JsonProperty("discard_rule_definitions")
	private List<DiscardRuleDefinition> discardRuleDefinitions;
	@JsonProperty("search_config_definitions")
	private List<SearchConfigDefinition> searchConfigDefinitions;
	@JsonProperty("alert_definitions")
	private List<Object> alertDefinitions;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Object> getInvoice() {
		return invoice;
	}

	public void setInvoice(List<Object> invoice) {
		this.invoice = invoice;
	}

	public List<String> getCommEmails() {
		return commEmails;
	}

	public void setCommEmails(List<String> commEmails) {
		this.commEmails = commEmails;
	}

	public String getCommCallback() {
		return commCallback;
	}

	public void setCommCallback(String commCallback) {
		this.commCallback = commCallback;
	}

	public PersonAnalytics getPersonAnalytics() {
		return personAnalytics;
	}

	public void setPersonAnalytics(PersonAnalytics personAnalytics) {
		this.personAnalytics = personAnalytics;
	}

	public List<Object> getBehaviourAnalytics() {
		return behaviourAnalytics;
	}

	public void setBehaviourAnalytics(List<Object> behaviourAnalytics) {
		this.behaviourAnalytics = behaviourAnalytics;
	}

	public List<ScoringDefinition> getScoringDefinitions() {
		return scoringDefinitions;
	}

	public void setScoringDefinitions(List<ScoringDefinition> scoringDefinitions) {
		this.scoringDefinitions = scoringDefinitions;
	}

	public List<Object> getPoiDefinitions() {
		return poiDefinitions;
	}

	public void setPoiDefinitions(List<Object> poiDefinitions) {
		this.poiDefinitions = poiDefinitions;
	}

	public List<Object> getFormDefinitions() {
		return formDefinitions;
	}

	public void setFormDefinitions(List<Object> formDefinitions) {
		this.formDefinitions = formDefinitions;
	}

	public List<Object> getCustomerGroupDefinitions() {
		return customerGroupDefinitions;
	}

	public void setCustomerGroupDefinitions(List<Object> customerGroupDefinitions) {
		this.customerGroupDefinitions = customerGroupDefinitions;
	}

	public List<Object> getCustomerDefinitions() {
		return customerDefinitions;
	}

	public void setCustomerDefinitions(List<Object> customerDefinitions) {
		this.customerDefinitions = customerDefinitions;
	}

	public List<DiscardRuleDefinition> getDiscardRuleDefinitions() {
		return discardRuleDefinitions;
	}

	public void setDiscardRuleDefinitions(List<DiscardRuleDefinition> discardRuleDefinitions) {
		this.discardRuleDefinitions = discardRuleDefinitions;
	}

	public List<SearchConfigDefinition> getSearchConfigDefinitions() {
		return searchConfigDefinitions;
	}

	public void setSearchConfigDefinitions(List<SearchConfigDefinition> searchConfigDefinitions) {
		this.searchConfigDefinitions = searchConfigDefinitions;
	}

	public List<Object> getAlertDefinitions() {
		return alertDefinitions;
	}

	public void setAlertDefinitions(List<Object> alertDefinitions) {
		this.alertDefinitions = alertDefinitions;
	}

}
