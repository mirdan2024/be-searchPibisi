
package it.search.pibisi.pojo.customer.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerFindResponse {

	@JsonProperty("data")
	private List<Datum> data;
	@JsonProperty("meta")
	private Meta meta;

	public List<Datum> getData() {
		return data;
	}

	public void setData(List<Datum> data) {
		this.data = data;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
