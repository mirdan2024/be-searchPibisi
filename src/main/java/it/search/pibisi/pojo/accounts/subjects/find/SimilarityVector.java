package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimilarityVector {

	@JsonProperty("id")
	private Id id;
	@JsonProperty("name")
	private Name name;
	@JsonProperty("birth.date")
	private BirthDate birthDate;
	@JsonProperty("death.date")
	private DeathDate deathDate;
	@JsonProperty("person")
	private Person person;
	@JsonProperty("function.current#country")
	private FunctionCurrentCountry functionCurrentCountry;
	@JsonProperty("function.current#scope")
	private FunctionCurrentScope functionCurrentScope;
	@JsonProperty("life_interval.birth")
	private LifeIntervalBirth lifeIntervalBirth;
	@JsonProperty("life_interval.death")
	private LifeIntervalDeath lifeIntervalDeath;

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public BirthDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(BirthDate birthDate) {
		this.birthDate = birthDate;
	}

	public DeathDate getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(DeathDate deathDate) {
		this.deathDate = deathDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public FunctionCurrentCountry getFunctionCurrentCountry() {
		return functionCurrentCountry;
	}

	public void setFunctionCurrentCountry(FunctionCurrentCountry functionCurrentCountry) {
		this.functionCurrentCountry = functionCurrentCountry;
	}

	public FunctionCurrentScope getFunctionCurrentScope() {
		return functionCurrentScope;
	}

	public void setFunctionCurrentScope(FunctionCurrentScope functionCurrentScope) {
		this.functionCurrentScope = functionCurrentScope;
	}

	public LifeIntervalBirth getLifeIntervalBirth() {
		return lifeIntervalBirth;
	}

	public void setLifeIntervalBirth(LifeIntervalBirth lifeIntervalBirth) {
		this.lifeIntervalBirth = lifeIntervalBirth;
	}

	public LifeIntervalDeath getLifeIntervalDeath() {
		return lifeIntervalDeath;
	}

	public void setLifeIntervalDeath(LifeIntervalDeath lifeIntervalDeath) {
		this.lifeIntervalDeath = lifeIntervalDeath;
	}

}
