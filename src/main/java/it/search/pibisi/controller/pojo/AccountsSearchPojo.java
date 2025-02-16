package it.search.pibisi.controller.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AccountsSearchPojo {

	private Integer idIntermediario;
	
	private String subjectId;

	private String content;

	private String birthDate;

	private String type;

	private String threshold;
	
	

	public Integer getIdIntermediario() {
		return idIntermediario;
	}

	public void setIdIntermediario(Integer idIntermediario) {
		this.idIntermediario = idIntermediario;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

}
