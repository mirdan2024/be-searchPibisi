package it.search.pibisi.pojo.customer.find.bycustomer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomersFindByCustomerResponse {
	@JsonProperty("data")
	private Data data;
	@JsonProperty("meta")
	private Meta meta;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
