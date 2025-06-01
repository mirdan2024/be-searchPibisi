package it.search.pibisi.pojo.customer.matches;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("customer_id")
	private String customerId;
	@JsonProperty("subject_id")
	private String subjectId;
	@JsonProperty("subject")
	private Subject subject;
	@JsonProperty("status")
	private String status;
	@JsonProperty("comment")
	private String comment;
	@JsonProperty("similarity")
	private double similarity;
	@JsonProperty("cardinality")
	private double cardinality;
	@JsonProperty("identity")
	private boolean identity;
	@JsonProperty("compatible")
	private boolean compatible;
	@JsonProperty("vector")
	private Vector vector;
	@JsonProperty("reason")
	private String reason;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("processed_at")
	private String processedAt;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public double getCardinality() {
		return cardinality;
	}

	public void setCardinality(double cardinality) {
		this.cardinality = cardinality;
	}

	public boolean isIdentity() {
		return identity;
	}

	public void setIdentity(boolean identity) {
		this.identity = identity;
	}

	public boolean isCompatible() {
		return compatible;
	}

	public void setCompatible(boolean compatible) {
		this.compatible = compatible;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(String processedAt) {
		this.processedAt = processedAt;
	}

}
