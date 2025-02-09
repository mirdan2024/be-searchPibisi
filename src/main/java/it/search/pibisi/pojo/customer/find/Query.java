
package it.search.pibisi.pojo.customer.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {

	@JsonProperty("status")
	private String status;
	@JsonProperty("search")
	private String search;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
