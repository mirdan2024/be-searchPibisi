
package it.search.pibisi.pojo.customer.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

	@JsonProperty("count")
	private Integer count;
	@JsonProperty("query")
	private Query query;
	@JsonProperty("page")
	private Integer page;
	@JsonProperty("total_count")
	private Integer totalCount;
	@JsonProperty("version")
	private String version;
	@JsonProperty("time")
	private String time;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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
