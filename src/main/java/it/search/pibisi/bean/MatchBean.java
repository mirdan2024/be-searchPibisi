package it.search.pibisi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchBean {

	private List<NameFullBean> nameFull;
	private HashMap<String, InfoBean> infoMap;
	private List<PoiBean> news;
	private List<PoiBean> function;
	private List<PoiBean> functionPublic;
	private List<PoiBean> functionPolitical;
	private List<PoiBean> sanction;
	private SubjectBean subjectBean;

	// Scoring Category HighRisk
	private Integer scoring;
	String typeCategory;
	String typeRisk;

	// Category
	private boolean isPep;
	private boolean wasPep;
	private String wasPepDate;
	private boolean isSanctioned;
	private boolean wasSanctioned;
	private String wasSanctionedDate;
	private boolean isTerrorist;
	private boolean hasMedia;
	private boolean hasAdverseInfo;
	private boolean isHighRisk;

	// record created
	private String createdAt;

	public List<PoiBean> getFunction() {
		if (function==null) {
			function = new ArrayList<PoiBean>();
		}
		return function;
	}

	public void setFunction(List<PoiBean> function) {
		this.function = function;
	}

	public List<PoiBean> getFunctionPublic() {
		if (functionPublic==null) {
			functionPublic = new ArrayList<PoiBean>();
		}
		return functionPublic;
	}

	public void setFunctionPublic(List<PoiBean> functionPublic) {
		this.functionPublic = functionPublic;
	}

	public List<PoiBean> getFunctionPolitical() {
		if (functionPolitical==null) {
			functionPolitical = new ArrayList<PoiBean>();
		}
		return functionPolitical;
	}

	public void setFunctionPolitical(List<PoiBean> functionPolitical) {
		this.functionPolitical = functionPolitical;
	}

	public List<PoiBean> getSanction() {
		if (sanction==null) {
			sanction = new ArrayList<PoiBean>();
		}
		return sanction;
	}

	public void setSanction(List<PoiBean> sanction) {
		this.sanction = sanction;
	}

	public HashMap<String, InfoBean> getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(HashMap<String, InfoBean> infoMap) {
		this.infoMap = infoMap;
	}

	public List<NameFullBean> getNameFull() {
		return nameFull;
	}

	public void setNameFull(List<NameFullBean> nameFull) {
		this.nameFull = nameFull;
	}

	public List<PoiBean> getNews() {
		return news;
	}

	public void setNews(List<PoiBean> news) {
		this.news = news;
	}

	public Integer getScoring() {
		return scoring;
	}

	public void setScoring(Integer scoring) {
		this.scoring = scoring;
	}

	public boolean isPep() {
		return isPep;
	}

	public void setPep(boolean isPep) {
		this.isPep = isPep;
	}

	public boolean isWasPep() {
		return wasPep;
	}

	public void setWasPep(boolean wasPep) {
		this.wasPep = wasPep;
	}

	public String getWasPepDate() {
		return wasPepDate;
	}

	public void setWasPepDate(String wasPepDate) {
		this.wasPepDate = wasPepDate;
	}

	public boolean isSanctioned() {
		return isSanctioned;
	}

	public void setSanctioned(boolean isSanctioned) {
		this.isSanctioned = isSanctioned;
	}

	public boolean isWasSanctioned() {
		return wasSanctioned;
	}

	public void setWasSanctioned(boolean wasSanctioned) {
		this.wasSanctioned = wasSanctioned;
	}

	public String getWasSanctionedDate() {
		return wasSanctionedDate;
	}

	public void setWasSanctionedDate(String wasSanctionedDate) {
		this.wasSanctionedDate = wasSanctionedDate;
	}

	public boolean isTerrorist() {
		return isTerrorist;
	}

	public void setTerrorist(boolean isTerrorist) {
		this.isTerrorist = isTerrorist;
	}

	public boolean isHasMedia() {
		return hasMedia;
	}

	public void setHasMedia(boolean hasMedia) {
		this.hasMedia = hasMedia;
	}

	public boolean isHasAdverseInfo() {
		return hasAdverseInfo;
	}

	public void setHasAdverseInfo(boolean hasAdverseInfo) {
		this.hasAdverseInfo = hasAdverseInfo;
	}

	public boolean isHighRisk() {
		return isHighRisk;
	}

	public void setHighRisk(boolean isHighRisk) {
		this.isHighRisk = isHighRisk;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public SubjectBean getSubjectBean() {
		return subjectBean;
	}

	public void setSubjectBean(SubjectBean subjectBean) {
		this.subjectBean = subjectBean;
	}

	public String getTypeCategory() {
		return typeCategory;
	}

	public void setTypeCategory(String typeCategory) {
		this.typeCategory = typeCategory;
	}

	public String getTypeRisk() {
		return typeRisk;
	}

	public void setTypeRisk(String typeRisk) {
		this.typeRisk = typeRisk;
	}
}
