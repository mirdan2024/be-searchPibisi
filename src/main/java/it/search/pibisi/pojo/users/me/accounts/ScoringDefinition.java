package it.search.pibisi.pojo.users.me.accounts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoringDefinition {

	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("weight")
	private Integer weight;
	@JsonProperty("path")
	private Object path;
	@JsonProperty("operator")
	private Object operator;
	@JsonProperty("value")
	private List<Object> value;
	@JsonProperty("params")
	private List<Object> params;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Object getPath() {
		return path;
	}

	public void setPath(Object path) {
		this.path = path;
	}

	public Object getOperator() {
		return operator;
	}

	public void setOperator(Object operator) {
		this.operator = operator;
	}

	public List<Object> getValue() {
		return value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

}
