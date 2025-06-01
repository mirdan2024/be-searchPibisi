package it.search.pibisi.pojo.customer.matches;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomersSubjectsResponse {

	@JsonProperty("data")
	private List<Data> data;

	@JsonProperty("meta")
	private Meta meta;

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
