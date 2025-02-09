package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimilarityVector__1 {

	@JsonProperty("id")
	private Id__1 id;
	@JsonProperty("name")
	private Name__1 name;
	@JsonProperty("birth.date")
	private BirthDate__1 birthDate;
	@JsonProperty("death.date")
	private DeathDate__1 deathDate;
	@JsonProperty("person")
	private Person__1 person;
	@JsonProperty("function.current#country")
	private FunctionCurrentCountry__1 functionCurrentCountry;
	@JsonProperty("function.current#scope")
	private FunctionCurrentScope__1 functionCurrentScope;
	@JsonProperty("life_interval.birth")
	private LifeIntervalBirth__1 lifeIntervalBirth;
	@JsonProperty("life_interval.death")
	private LifeIntervalDeath__1 lifeIntervalDeath;

	public Id__1 getId() {
		return id;
	}

	public void setId(Id__1 id) {
		this.id = id;
	}

	public Name__1 getName() {
		return name;
	}

	public void setName(Name__1 name) {
		this.name = name;
	}

	public BirthDate__1 getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(BirthDate__1 birthDate) {
		this.birthDate = birthDate;
	}

	public DeathDate__1 getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(DeathDate__1 deathDate) {
		this.deathDate = deathDate;
	}

	public Person__1 getPerson() {
		return person;
	}

	public void setPerson(Person__1 person) {
		this.person = person;
	}

	public FunctionCurrentCountry__1 getFunctionCurrentCountry() {
		return functionCurrentCountry;
	}

	public void setFunctionCurrentCountry(FunctionCurrentCountry__1 functionCurrentCountry) {
		this.functionCurrentCountry = functionCurrentCountry;
	}

	public FunctionCurrentScope__1 getFunctionCurrentScope() {
		return functionCurrentScope;
	}

	public void setFunctionCurrentScope(FunctionCurrentScope__1 functionCurrentScope) {
		this.functionCurrentScope = functionCurrentScope;
	}

	public LifeIntervalBirth__1 getLifeIntervalBirth() {
		return lifeIntervalBirth;
	}

	public void setLifeIntervalBirth(LifeIntervalBirth__1 lifeIntervalBirth) {
		this.lifeIntervalBirth = lifeIntervalBirth;
	}

	public LifeIntervalDeath__1 getLifeIntervalDeath() {
		return lifeIntervalDeath;
	}

	public void setLifeIntervalDeath(LifeIntervalDeath__1 lifeIntervalDeath) {
		this.lifeIntervalDeath = lifeIntervalDeath;
	}

}
