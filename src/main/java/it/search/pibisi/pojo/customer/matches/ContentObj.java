package it.search.pibisi.pojo.customer.matches;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContentObj {

	@JsonProperty("number")
	private String number;
	@JsonProperty("platform")
	private String platform;
	@JsonProperty("reason")
	private String reason;
	@JsonProperty("issuer")
	private String issuer;
	@JsonProperty("from")
	private String from;
	@JsonProperty("summary")
	private String summary;
	@JsonProperty("program")
	private String program;
	@JsonProperty("program_description")
	private String programDescription;
	@JsonProperty("program_source")
	private String programSource;
	@JsonProperty("types")
	private List<String> types;

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

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@JsonProperty("country")
	private String country;

}
