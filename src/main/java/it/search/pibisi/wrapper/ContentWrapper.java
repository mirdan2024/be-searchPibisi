package it.search.pibisi.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContentWrapper {

	@JsonProperty("types")
	private List<String> types;

	@JsonProperty("summary")
	private String summary;

	@JsonProperty("issuer")
	private String issuer;

	@JsonProperty("country")
	private String country;

	@JsonProperty("from")
	private String from;

	@JsonProperty("to")
	private String to;

	@JsonProperty("url")
	private String url;

	@JsonProperty("city")
	private String city;

	@JsonProperty("organization")
	private String organization;

	@JsonProperty("charge")
	private String charge;

	@JsonProperty("number")
	private String number;

	@JsonProperty("platform")
	private String platform;

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("program")
	private String program;

	@JsonProperty("program_description")
	private String programDescription;

	@JsonProperty("program_source")
	private String programSource;

	public ContentWrapper() {
		// Costruttore
	}

	// Getter e Setter
	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getProgramDescription() {
		return programDescription;
	}

	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}

	public String getProgramSource() {
		return programSource;
	}

	public void setProgramSource(String programSource) {
		this.programSource = programSource;
	}

}
