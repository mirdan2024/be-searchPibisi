package it.search.pibisi.pojo.customer.matches;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vector {

	@JsonProperty("id")
	private VectorField id;
	@JsonProperty("name")
	private VectorField name;
	@JsonProperty("person")
	private VectorField person;
	@JsonProperty("birth_date")
	private VectorField birthDate;
	@JsonProperty("death_date")
	private VectorField deathDate;
	@JsonProperty("life_interval_birth")
	private VectorField lifeIntervalBirth;
	@JsonProperty("life_interval_death")
	private VectorField lifeIntervalDeath;
	@JsonProperty("function_current_scope")
	private VectorField functionCurrentScope;
	@JsonProperty("function_current_country")
	private VectorField functionCurrentCountry;

	public VectorField getId() {
		return id;
	}

	public void setId(VectorField id) {
		this.id = id;
	}

	public VectorField getName() {
		return name;
	}

	public void setName(VectorField name) {
		this.name = name;
	}

	public VectorField getPerson() {
		return person;
	}

	public void setPerson(VectorField person) {
		this.person = person;
	}

	public VectorField getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(VectorField birthDate) {
		this.birthDate = birthDate;
	}

	public VectorField getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(VectorField deathDate) {
		this.deathDate = deathDate;
	}

	public VectorField getLifeIntervalBirth() {
		return lifeIntervalBirth;
	}

	public void setLifeIntervalBirth(VectorField lifeIntervalBirth) {
		this.lifeIntervalBirth = lifeIntervalBirth;
	}

	public VectorField getLifeIntervalDeath() {
		return lifeIntervalDeath;
	}

	public void setLifeIntervalDeath(VectorField lifeIntervalDeath) {
		this.lifeIntervalDeath = lifeIntervalDeath;
	}

	public VectorField getFunctionCurrentScope() {
		return functionCurrentScope;
	}

	public void setFunctionCurrentScope(VectorField functionCurrentScope) {
		this.functionCurrentScope = functionCurrentScope;
	}

	public VectorField getFunctionCurrentCountry() {
		return functionCurrentCountry;
	}

	public void setFunctionCurrentCountry(VectorField functionCurrentCountry) {
		this.functionCurrentCountry = functionCurrentCountry;
	}

}
