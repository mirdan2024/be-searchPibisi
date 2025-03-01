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
	
	//SubjectPoiBean
	List<SubjectPoiBean> subjectNameFull;
	List<SubjectPoiBean> subjectPerson;
	List<SubjectPoiBean> subjectGender;
	List<SubjectPoiBean> subjectBirthDate;
	List<SubjectPoiBean> subjectBirthPlace;
	List<SubjectPoiBean> subjectNationality;
	List<SubjectPoiBean> subjectIllegal;
	List<SubjectPoiBean> subjectIdPlatform;
	List<SubjectPoiBean> subjectPlatform;
	List<SubjectPoiBean> subjectNameFirst;
	List<SubjectPoiBean> subjectNameLast;
	List<SubjectPoiBean> subjectIdPassport;
	List<SubjectPoiBean> subjectPhoto;
	List<SubjectPoiBean> subjectFunction;
	List<SubjectPoiBean> subjectFunctionPublic;
	List<SubjectPoiBean> subjectFunctionPolitical;
	List<SubjectPoiBean> subjectSanction;
	List<SubjectPoiBean> subjectMedia;
	List<SubjectPoiBean> subjectDead;
	List<SubjectPoiBean> subjectRelationRelative;
	
	
	
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

	public List<SubjectPoiBean> getSubjectNameFull() {
		return subjectNameFull;
	}

	public void setSubjectNameFull(List<SubjectPoiBean> subjectNameFull) {
		this.subjectNameFull = subjectNameFull;
	}

	public List<SubjectPoiBean> getSubjectPerson() {
		return subjectPerson;
	}

	public void setSubjectPerson(List<SubjectPoiBean> subjectPerson) {
		this.subjectPerson = subjectPerson;
	}

	public List<SubjectPoiBean> getSubjectGender() {
		return subjectGender;
	}

	public void setSubjectGender(List<SubjectPoiBean> subjectGender) {
		this.subjectGender = subjectGender;
	}

	public List<SubjectPoiBean> getSubjectBirthDate() {
		return subjectBirthDate;
	}

	public void setSubjectBirthDate(List<SubjectPoiBean> subjectBirthDate) {
		this.subjectBirthDate = subjectBirthDate;
	}

	public List<SubjectPoiBean> getSubjectBirthPlace() {
		return subjectBirthPlace;
	}

	public void setSubjectBirthPlace(List<SubjectPoiBean> subjectBirthPlace) {
		this.subjectBirthPlace = subjectBirthPlace;
	}

	public List<SubjectPoiBean> getSubjectNationality() {
		return subjectNationality;
	}

	public void setSubjectNationality(List<SubjectPoiBean> subjectNationality) {
		this.subjectNationality = subjectNationality;
	}

	public List<SubjectPoiBean> getSubjectIllegal() {
		return subjectIllegal;
	}

	public void setSubjectIllegal(List<SubjectPoiBean> subjectIllegal) {
		this.subjectIllegal = subjectIllegal;
	}

	public List<SubjectPoiBean> getSubjectIdPlatform() {
		return subjectIdPlatform;
	}

	public void setSubjectIdPlatform(List<SubjectPoiBean> subjectIdPlatform) {
		this.subjectIdPlatform = subjectIdPlatform;
	}

	public List<SubjectPoiBean> getSubjectPlatform() {
		return subjectPlatform;
	}

	public void setSubjectPlatform(List<SubjectPoiBean> subjectPlatform) {
		this.subjectPlatform = subjectPlatform;
	}

	public List<SubjectPoiBean> getSubjectNameFirst() {
		return subjectNameFirst;
	}

	public void setSubjectNameFirst(List<SubjectPoiBean> subjectNameFirst) {
		this.subjectNameFirst = subjectNameFirst;
	}

	public List<SubjectPoiBean> getSubjectNameLast() {
		return subjectNameLast;
	}

	public void setSubjectNameLast(List<SubjectPoiBean> subjectNameLast) {
		this.subjectNameLast = subjectNameLast;
	}

	public List<SubjectPoiBean> getSubjectIdPassport() {
		return subjectIdPassport;
	}

	public void setSubjectIdPassport(List<SubjectPoiBean> subjectIdPassport) {
		this.subjectIdPassport = subjectIdPassport;
	}

	public List<SubjectPoiBean> getSubjectPhoto() {
		return subjectPhoto;
	}

	public void setSubjectPhoto(List<SubjectPoiBean> subjectPhoto) {
		this.subjectPhoto = subjectPhoto;
	}

	public List<SubjectPoiBean> getSubjectFunction() {
		return subjectFunction;
	}

	public void setSubjectFunction(List<SubjectPoiBean> subjectFunction) {
		this.subjectFunction = subjectFunction;
	}

	public List<SubjectPoiBean> getSubjectFunctionPublic() {
		return subjectFunctionPublic;
	}

	public void setSubjectFunctionPublic(List<SubjectPoiBean> subjectFunctionPublic) {
		this.subjectFunctionPublic = subjectFunctionPublic;
	}

	public List<SubjectPoiBean> getSubjectFunctionPolitical() {
		return subjectFunctionPolitical;
	}

	public void setSubjectFunctionPolitical(List<SubjectPoiBean> subjectFunctionPolitical) {
		this.subjectFunctionPolitical = subjectFunctionPolitical;
	}

	public List<SubjectPoiBean> getSubjectSanction() {
		return subjectSanction;
	}

	public void setSubjectSanction(List<SubjectPoiBean> subjectSanction) {
		this.subjectSanction = subjectSanction;
	}

	public List<SubjectPoiBean> getSubjectMedia() {
		return subjectMedia;
	}

	public void setSubjectMedia(List<SubjectPoiBean> subjectMedia) {
		this.subjectMedia = subjectMedia;
	}

	public List<SubjectPoiBean> getSubjectDead() {
		return subjectDead;
	}

	public void setSubjectDead(List<SubjectPoiBean> subjectDead) {
		this.subjectDead = subjectDead;
	}

	public List<SubjectPoiBean> getSubjectRelationRelative() {
		return subjectRelationRelative;
	}

	public void setSubjectRelationRelative(List<SubjectPoiBean> subjectRelationRelative) {
		this.subjectRelationRelative = subjectRelationRelative;
	}
	
	
	
	
	
	
	
	
	
	
	

	public void addSubjectNameFull(SubjectPoiBean subjectNameFull) {
		if(getSubjectNameFull()==null)
			this.subjectNameFull=new ArrayList<>();
		
		getSubjectNameFull().add(subjectNameFull);
	}

	public void addSubjectPerson(SubjectPoiBean subjectPerson) {
		if(getSubjectPerson()==null)
			this.subjectPerson=new ArrayList<>();
		
		getSubjectPerson().add(subjectPerson);
	}

	public void addSubjectGender(SubjectPoiBean subjectGender) {
		if(getSubjectGender()==null)
			this.subjectGender=new ArrayList<>();
		
		getSubjectGender().add(subjectGender);
	}

	public void addSubjectBirthDate(SubjectPoiBean subjectBirthDate) {
		if(getSubjectBirthDate()==null)
			this.subjectBirthDate=new ArrayList<>();
		
		getSubjectBirthDate().add(subjectBirthDate);
	}




	public void addSubjectBirthPlace(SubjectPoiBean subjectBirthPlace) {
		if(getSubjectBirthPlace()==null)
			this.subjectBirthPlace=new ArrayList<>();
		
		getSubjectBirthPlace().add(subjectBirthPlace);
	}

	public void addSubjectNationality(SubjectPoiBean subjectNationality) {
		if(getSubjectNationality()==null)
			this.subjectNationality=new ArrayList<>();
		
		getSubjectNationality().add(subjectNationality);
	}


	public void addSubjectIllegal(SubjectPoiBean subjectIllegal) {
		if(getSubjectIllegal()==null)
			this.subjectIllegal=new ArrayList<>();
		
		getSubjectIllegal().add(subjectIllegal);
	}

	public void addSubjectIdPlatform(SubjectPoiBean subjectIdPlatform) {
		if(getSubjectIdPlatform()==null)
			this.subjectIdPlatform=new ArrayList<>();
		
		getSubjectIdPlatform().add(subjectIdPlatform);
	}

	public void addSubjectPlatform(SubjectPoiBean subjectPlatform) {
		if(getSubjectPlatform()==null)
			this.subjectPlatform=new ArrayList<>();
		
		getSubjectPlatform().add(subjectPlatform);
	}

	public void addSubjectDead(SubjectPoiBean subjectDead) {
		if(getSubjectDead()==null)
			this.subjectDead=new ArrayList<>();
		
		getSubjectDead().add(subjectDead);
	}

	public void addSubjectNameFirst(SubjectPoiBean subjectNameFirst) {
		if(getSubjectNameFirst()==null)
			this.subjectNameFirst=new ArrayList<>();
		
		getSubjectNameFirst().add(subjectNameFirst);
	}


	public void addSubjectNameLast(SubjectPoiBean subjectNameLast) {
		if(getSubjectNameLast()==null)
			this.subjectNameLast=new ArrayList<>();
		
		getSubjectNameLast().add(subjectNameLast);
	}



	public void addSubjectIdPassport(SubjectPoiBean subjectIdPassport) {
		if(getSubjectIdPassport()==null)
			this.subjectIdPassport=new ArrayList<>();
		
		getSubjectIdPassport().add(subjectIdPassport);
	}



	public void addSubjectPhoto(SubjectPoiBean subjectPhoto) {
		if(getSubjectPhoto()==null)
			this.subjectPhoto=new ArrayList<>();
		
		getSubjectPhoto().add(subjectPhoto);
	}


	public void addSubjectFunction(SubjectPoiBean subjectFunction) {
		if(getSubjectFunction()==null)
			this.subjectFunction=new ArrayList<>();
		
		getSubjectFunction().add(subjectFunction);
	}



	public void addSubjectFunctionPublic(SubjectPoiBean subjectFunctionPublic) {
		if(getSubjectFunctionPublic()==null)
			this.subjectFunctionPublic=new ArrayList<>();
		
		getSubjectFunctionPublic().add(subjectFunctionPublic);
	}

	public void addSubjectFunctionPolitical(SubjectPoiBean subjectFunctionPolitical) {
		if(getSubjectFunctionPolitical()==null)
			this.subjectFunctionPolitical=new ArrayList<>();
		
		getSubjectFunctionPolitical().add(subjectFunctionPolitical);
	}


	public void addSubjectSanction(SubjectPoiBean subjectSanction) {
		if(getSubjectSanction()==null)
			this.subjectSanction=new ArrayList<>();
		
		getSubjectSanction().add(subjectSanction);
	}


	public void addSubjectMedia(SubjectPoiBean subjectMedia) {
		if(getSubjectMedia()==null)
			this.subjectMedia=new ArrayList<>();
		
		getSubjectMedia().add(subjectMedia);
	}

	public void addSubjectRelationRelative(SubjectPoiBean subjectRelationRelative) {
		if(getSubjectRelationRelative()==null)
			this.subjectRelationRelative=new ArrayList<>();
		
		getSubjectRelationRelative().add(subjectRelationRelative);
	}

	
}
