package it.search.pibisi.pojo.customer.matches;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VectorField {

	@JsonProperty("exact")
	private int exact;
	@JsonProperty("fuzzy")
	private String fuzzy;
	@JsonProperty("poi_uuids_1")
	private List<String> poiUuids1;
	@JsonProperty("poi_uuids_2")
	private List<String> poiUuids2;

	public int getExact() {
		return exact;
	}

	public void setExact(int exact) {
		this.exact = exact;
	}

	public String getFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(String fuzzy) {
		this.fuzzy = fuzzy;
	}

	public List<String> getPoiUuids1() {
		return poiUuids1;
	}

	public void setPoiUuids1(List<String> poiUuids1) {
		this.poiUuids1 = poiUuids1;
	}

	public List<String> getPoiUuids2() {
		return poiUuids2;
	}

	public void setPoiUuids2(List<String> poiUuids2) {
		this.poiUuids2 = poiUuids2;
	}

}
