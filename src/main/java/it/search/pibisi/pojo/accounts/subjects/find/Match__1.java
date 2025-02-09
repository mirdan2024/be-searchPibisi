package it.search.pibisi.pojo.accounts.subjects.find;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Match__1 {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("info")
	private List<Info> info;
	@JsonProperty("similarity")
	private Integer similarity;
	@JsonProperty("cardinality")
	private Double cardinality;
	@JsonProperty("similarity_vector")
	private SimilarityVector__1 similarityVector;
	@JsonProperty("scoring")
	private Scoring scoring;
	@JsonProperty("subject")
	private Subject subject;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Info> getInfo() {
		return info;
	}

	public void setInfo(List<Info> info) {
		this.info = info;
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

	public SimilarityVector__1 getSimilarityVector() {
		return similarityVector;
	}

	public void setSimilarityVector(SimilarityVector__1 similarityVector) {
		this.similarityVector = similarityVector;
	}

	public Scoring getScoring() {
		return scoring;
	}

	public void setScoring(Scoring scoring) {
		this.scoring = scoring;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
