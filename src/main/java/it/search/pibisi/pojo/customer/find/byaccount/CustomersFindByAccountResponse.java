package it.search.pibisi.pojo.customer.find.byaccount;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomersFindByAccountResponse {

	@JsonProperty("data")
	private List<DataItem> data;
	@JsonProperty("meta")
	private Meta meta;

	public List<DataItem> getData() {
		return data;
	}

	public void setData(List<DataItem> data) {
		this.data = data;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
