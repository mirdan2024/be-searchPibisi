package it.search.pibisi.pojo.accounts.subjects.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LifeIntervalBirth {

	@JsonProperty("fuzzy")
	private String fuzzy;
	@JsonProperty("exact")
	private Integer exact;
	@JsonProperty("poi_uuids_1")
	private List<Object> poiUuids1;
	@JsonProperty("poi_uuids_2")
	private List<Object> poiUuids2;

	public String getFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(String fuzzy) {
		this.fuzzy = fuzzy;
	}

	public Integer getExact() {
		return exact;
	}

	public void setExact(Integer exact) {
		this.exact = exact;
	}

	public List<Object> getPoiUuids1() {
		return poiUuids1;
	}

	public void setPoiUuids1(List<Object> poiUuids1) {
		this.poiUuids1 = poiUuids1;
	}

	public List<Object> getPoiUuids2() {
		return poiUuids2;
	}

	public void setPoiUuids2(List<Object> poiUuids2) {
		this.poiUuids2 = poiUuids2;
	}

}
