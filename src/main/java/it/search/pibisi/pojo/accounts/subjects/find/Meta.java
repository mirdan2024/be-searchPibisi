package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

	@JsonProperty("total_count")
	private Integer totalCount;
	@JsonProperty("count")
	private Integer count;
	@JsonProperty("page")
	private Integer page;
	@JsonProperty("page_size")
	private Integer pageSize;
	@JsonProperty("add")
	private Boolean add;
	@JsonProperty("query")
	private Query query;
	@JsonProperty("version")
	private String version;
	@JsonProperty("time")
	private String time;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getAdd() {
		return add;
	}

	public void setAdd(Boolean add) {
		this.add = add;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
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
