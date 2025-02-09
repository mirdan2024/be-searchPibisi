package it.search.pibisi.pojo.accounts.subjects.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Match {

	@JsonProperty("subject")
	private String subject;
	@JsonProperty("similarity")
	private Integer similarity;
	@JsonProperty("cardinality")
	private Double cardinality;
	@JsonProperty("similarity_vector")
	private SimilarityVector similarityVector;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Integer similarity) {
		this.similarity = similarity;
	}

	public Double getCardinality() {
		return cardinality;
	}

	public void setCardinality(Double cardinality) {
		this.cardinality = cardinality;
	}

	public SimilarityVector getSimilarityVector() {
		return similarityVector;
	}

	public void setSimilarityVector(SimilarityVector similarityVector) {
		this.similarityVector = similarityVector;
	}

}
