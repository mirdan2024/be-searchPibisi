package it.search.pibisi.pojo.customer.find.byaccount;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

	@JsonProperty("count")
	private int count;
	@JsonProperty("query")
	private Query query;
	@JsonProperty("page")
	private int page;
	@JsonProperty("total_count")
	private int totalCount;
	@JsonProperty("all_customers_count")
	private int allCustomersCount;
	@JsonProperty("version")
	private String version;
	@JsonProperty("time")
	private String time;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getAllCustomersCount() {
		return allCustomersCount;
	}

	public void setAllCustomersCount(int allCustomersCount) {
		this.allCustomersCount = allCustomersCount;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
