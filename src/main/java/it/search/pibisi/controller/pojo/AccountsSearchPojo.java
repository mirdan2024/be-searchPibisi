package it.search.pibisi.controller.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AccountsSearchPojo {

	// Settato dal JWT
	private String accountId;

	private String subjectId;

	// Example: {"type": "name.full", "content": "Walt Disney"}
	private String nameFull;

	// Example: {"type": "birth.date", "content": "2019-03-15"}
	private String birthDate;

	// Example: {"type": "birth.place", "content": {"country": "ESP", "city":
	// "Málaga"}}
	private String birthPlace;

	// Example 1: {"type": "gender", "content": "F"} or Example 2: {"type":
	// "gender", "content": "M"}
	private String gender;

	// Example: {"type": "nationality", "content": "ESP"}
	private String nationality;

	// Example 1: {"type": "person","content": "P"} or Example 2:{"type":
	// "person","content": "E"}
	private String person;

	private String threshold;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getNameFull() {
		return nameFull;
	}

	public void setNameFull(String nameFull) {
		this.nameFull = nameFull;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	// PARAMETRI TYPE
	public String getNameFullType() {
		return "name.full";
	}

	public String getBirthDateType() {
		return "birth.date";
	}

	public String getBirthPlaceType() {
		return "birth.place";
	}

	public String getGenderType() {
		return "gender";
	}

	public String getNationalityType() {
		return "nationality";
	}

	public String getPersonType() {
		return "person";
	}

	// PARAMETRI PER CUSTOM
	private String customerId;

	private String matchId;

	private String search;

	private String risk;

	private String comment;

	private String data;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
