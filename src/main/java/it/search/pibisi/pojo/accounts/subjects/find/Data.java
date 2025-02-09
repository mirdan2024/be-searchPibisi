package it.search.pibisi.pojo.accounts.subjects.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	@JsonProperty("search_result")
	private SearchResult searchResult;
	@JsonProperty("matches")
	private List<Match__1> matches;
	@JsonProperty("discarded_matches")
	private List<Object> discardedMatches;

	public SearchResult getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	public List<Match__1> getMatches() {
		return matches;
	}

	public void setMatches(List<Match__1> matches) {
		this.matches = matches;
	}

	public List<Object> getDiscardedMatches() {
		return discardedMatches;
	}

	public void setDiscardedMatches(List<Object> discardedMatches) {
		this.discardedMatches = discardedMatches;
	}

}
