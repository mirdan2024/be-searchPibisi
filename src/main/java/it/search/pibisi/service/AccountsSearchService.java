package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.base.ListaCategorieGruppoPojo;
import it.search.pibisi.bean.InfoBean;
import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.bean.NameFullBean;
import it.search.pibisi.bean.PoiBean;
import it.search.pibisi.bean.SoiBean;
import it.search.pibisi.bean.SubjectBean;
import it.search.pibisi.bean.SubjectInfoBean;
import it.search.pibisi.bean.SubjectPoiBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.accounts.subjects.find.Info__2;
import it.search.pibisi.pojo.accounts.subjects.find.Match__1;
import it.search.pibisi.pojo.accounts.subjects.find.Scoring;
import it.search.pibisi.pojo.accounts.subjects.find.Soi;
import it.search.pibisi.pojo.accounts.subjects.find.Subject;
import it.search.pibisi.wrapper.ContentWrapper;
import it.search.pibisi.wrapper.SoiWrapper;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AccountsSearchService extends BaseService {

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private UtilsService utilsService;

	// Metodo per fare una richiesta di ricerca
	public MatchListBean search(AccountsSearchPojo requestJson, HttpServletRequest request) {

		try {

			MatchListBean matchListBean = readMatchSearch(searchMatch(requestJson));

			ListaCategorieGruppoPojo lcgp = utilsService.callGetListaCategorie(requestJson, request);
			List<String> listCategorie = new ArrayList<>();
			lcgp.getCategorieGruppoPojo().forEach(e -> {
				e.getListaCategorie().forEach(c -> {
					listCategorie.add(c.getCategoria());
				});
			});

			MatchListBean matchListBeanResponse = new MatchListBean();
			matchListBean.getElencoMatch().forEach(e -> {
				if (Boolean.TRUE.equals(matchCategory(e, listCategorie))) {
					matchListBeanResponse.addMatchBean(e);
				}
			});

			return matchListBeanResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Boolean matchCategory(MatchBean matchBean, List<String> listCategorie) {
		if ((matchBean.isPep() || matchBean.isWasPep()) && !listCategorie.contains("PEP")) {
			return Boolean.FALSE;
		}
		if ((matchBean.isSanctioned() || matchBean.isWasSanctioned()) && !listCategorie.contains("Sanction")) {
			return Boolean.FALSE;
		}
		if (matchBean.isTerrorist() && !listCategorie.contains("Terrorist")) {
			return Boolean.FALSE;
		}
		if (matchBean.isHasMedia() && !listCategorie.contains("Adverse media")) {
			return Boolean.FALSE;
		}
		if (matchBean.isHasAdverseInfo() && !listCategorie.contains("Adverse info")) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	private MatchListBean readMatchSearch(AccountsSubjectsFindResponse accountsSubjectsFindResponse)
			throws IOException {
		MatchListBean matchListBean = new MatchListBean();

		// Read tag data
		if (accountsSubjectsFindResponse != null && accountsSubjectsFindResponse.getData() != null
				&& accountsSubjectsFindResponse.getData().getMatches() != null
				&& accountsSubjectsFindResponse.getData().getMatches() != null) {

			// Read tag match
			for (Match__1 match : accountsSubjectsFindResponse.getData().getMatches()) {

				MatchBean matchBean = new MatchBean();
				matchBean.setNameFull(new ArrayList<>());
				matchBean.setInfoMap(new HashMap<>());
				matchBean.setNews(new ArrayList<>());

				if (match.getScoring() != null)
					readScoringAndCategory(match.getScoring(), matchBean);

				if (match.getSubject() != null)
					readSubjectBean(match.getSubject(), matchBean);

				if (match.getInfo() != null)
					readInfo(match.getInfo(), matchBean);

				readDate(match, matchBean);

				if (match.getSubject() != null)
					readNewSubjectBean(match.getSubject(), matchBean);

				matchListBean.addMatchBean(matchBean);
			}
		}
		return matchListBean;
	}

	// Start read info in data -> matches
	private void readNewSubjectBean(Subject subject, MatchBean matchBean) throws IOException {
		matchBean.setSubjectBean(new SubjectBean());
		if (subject.getCreatedAt() != null)
			matchBean.getSubjectBean().setCreatedAt(subject.getCreatedAt().getDate());
		if (subject.getUuid() != null)
			matchBean.getSubjectBean().setUuid(subject.getUuid());

		matchBean.getSubjectBean().setSubjectInfoMap(new HashMap<>());
		if (subject.getInfo() != null)
			readSubjectInfo(matchBean.getSubjectBean().getSubjectInfoMap(), subject.getInfo());

		for (Info__2 info : subject.getInfo()) {
			SubjectPoiBean subjectPoiBean = new SubjectPoiBean();
			if (info.getContent() != null)
				subjectPoiBean.setContent(info.getContent().toString());
			if (info.getGroup() != null)
				subjectPoiBean.setGroup(info.getGroup().toString());
			subjectPoiBean.setUuid(info.getUuid());
			subjectPoiBean.setType(info.getType());
			subjectPoiBean.setSoiBean(new ArrayList<>());

			if (info.getSois() != null) {
				for (Soi soi : info.getSois()) {
					if (Boolean.TRUE.equals(soi.getActive())) {
						ObjectMapper objectMapper = new ObjectMapper();
						objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						// Deserializza il JSON in un oggetto SoiWrapper
						SoiWrapper soiWrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(soi),
								SoiWrapper.class);

						SoiBean soiBean = new SoiBean();
						BeanUtils.copyProperties(soiWrapper, soiBean);

						subjectPoiBean.getSoiBean().add(soiBean);
					}
				}

				switch (info.getType()) {
				case "name.full":
					matchBean.addSubjectNameFull(subjectPoiBean);
					break;
				case "person":
					matchBean.addSubjectPerson(subjectPoiBean);
					break;
				case "gender":
					matchBean.addSubjectGender(subjectPoiBean);
					break;
				case "birth.date":
					matchBean.addSubjectBirthDate(subjectPoiBean);
					break;
				case "birth.place":
					matchBean.addSubjectBirthPlace(subjectPoiBean);
					break;
				case "nationality":
					matchBean.addSubjectNationality(subjectPoiBean);
					break;
				case "illegal":
					matchBean.addSubjectIllegal(subjectPoiBean);
					break;
				case "id.platform":
					matchBean.addSubjectIdPlatform(subjectPoiBean);
					break;
				case "platform":
					matchBean.addSubjectPlatform(subjectPoiBean);
					break;
				case "name.first":
					matchBean.addSubjectNameFirst(subjectPoiBean);
					break;
				case "name.last":
					matchBean.addSubjectNameLast(subjectPoiBean);
					break;
				case "id.passport":
					matchBean.addSubjectIdPassport(subjectPoiBean);
					break;
				case "photo":
					matchBean.addSubjectPhoto(subjectPoiBean);
					break;
				case "function":
					matchBean.addSubjectFunction(subjectPoiBean);
					break;
				case "function.public":
					matchBean.addSubjectFunctionPublic(subjectPoiBean);
					break;
				case "function.political":
					matchBean.addSubjectFunctionPolitical(subjectPoiBean);
					break;
				case "sanction":
					matchBean.addSubjectSanction(subjectPoiBean);
					break;
				case "media":
					matchBean.addSubjectMedia(subjectPoiBean);
					break;
				case "dead":
					matchBean.addSubjectDead(subjectPoiBean);
					break;
				case "relation.relative":
					matchBean.addSubjectRelationRelative(subjectPoiBean);
					break;

				default:
					logUnknownInfo2(info);
					break;
				}
			}
		}

	}

	// Start read info in data -> matches
	private void readInfo(List<it.search.pibisi.pojo.accounts.subjects.find.Info> infoList, MatchBean matchBean)
			throws IOException {
		for (it.search.pibisi.pojo.accounts.subjects.find.Info info : infoList) {
			switch (info.getType()) {
			case "name.full":
				addNameFullBean(matchBean, info);
				break;
			case "person", "gender", "birth.date", "birth.place", "nationality", "illegal", "id.platform", "name.first",
					"name.last", "id.passport", "photo":
				setMapInfo(matchBean, info, info.getType());
				break;
			case "function":
				addPoisBean(matchBean.getFunction(), info);
				break;
			case "function.public":
				addPoisBean(matchBean.getFunctionPublic(), info);
				break;
			case "function.political":
				addPoisBean(matchBean.getFunctionPolitical(), info);
				break;
			case "sanction":
				addPoisBean(matchBean.getSanction(), info);
				break;

			case "media":
				addPoisBean(matchBean.getNews(), info);
				break;
			default:
				logUnknownInfo(info);
				break;
			}
		}
	}

	private void addNameFullBean(MatchBean matchList, it.search.pibisi.pojo.accounts.subjects.find.Info info) {
		NameFullBean nameFullBean = new NameFullBean();
		nameFullBean.setUuid(info.getUuid());
		nameFullBean.setType(info.getType());
		nameFullBean.setContent(String.valueOf(info.getContent()));
		nameFullBean.setGroup(String.valueOf(info.getGroup()));
		matchList.getNameFull().add(nameFullBean);
	}

	private void setMapInfo(MatchBean matchList, it.search.pibisi.pojo.accounts.subjects.find.Info info,
			String fieldName) throws IOException {
		InfoBean infoBean = new InfoBean();
		infoBean.setUuid(info.getUuid());
		infoBean.setType(info.getType());
		infoBean.setContent(String.valueOf(info.getContent()));
		infoBean.setGroup(String.valueOf(info.getGroup()));

		if ("birth.place".equals(fieldName) || "function".equals(fieldName) || "sanction".equals(fieldName)
				|| "id.passport".equals(fieldName) || "id.platform".equals(fieldName) || "photo".equals(fieldName)
				|| "function.public".equals(fieldName)) {
			// Crea un'istanza dell'ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// Deserializza il JSON in un oggetto Wrapper
			ContentWrapper wrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(info.getContent()),
					ContentWrapper.class);

			switch (fieldName) {
			case "birth.place": {
				infoBean.setCity(wrapper.getCity());
				infoBean.setCountry(wrapper.getCountry());
				break;
			}
			case "function": {
				infoBean.setCharge(wrapper.getCharge());
				infoBean.setOrganization(wrapper.getOrganization());
				break;
			}
			case "sanction": {
				infoBean.setReason(wrapper.getReason());
				infoBean.setIssuer(wrapper.getIssuer());
				infoBean.setProgram(wrapper.getProgram());
				infoBean.setProgramDescription(wrapper.getProgramDescription());
				infoBean.setProgramSource(wrapper.getProgramSource());
				infoBean.setTypes(wrapper.getTypes());
				break;
			}
			case "id.passport": {
				infoBean.setNumber(wrapper.getNumber());
				infoBean.setCountry(wrapper.getCountry());
				break;
			}
			case "id.platform": {
				infoBean.setNumber(wrapper.getNumber());
				infoBean.setPlatform(wrapper.getPlatform());
				break;
			}
			case "photo": {
				infoBean.setUrl(wrapper.getUrl());
				break;
			}
			case "function.public": {
				infoBean.setCharge(wrapper.getCharge());
				infoBean.setOrganization(wrapper.getOrganization());
				infoBean.setFrom(wrapper.getFrom());
				infoBean.setTo(wrapper.getTo());
				infoBean.setCountry(wrapper.getCountry());
				break;
			}
			default:
				System.out.println("ERROR: mancante fieldName    --> " + fieldName);
			}
		}

		matchList.getInfoMap().put(fieldName, infoBean);
	}

//	private void addNewsBean(MatchBean matchList, it.search.pibisi.pojo.accounts.subjects.find.Info info)
//			throws IOException {
//		// Crea un'istanza dell'ObjectMapper
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		// Deserializza il JSON in un oggetto Wrapper
//		ContentWrapper wrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(info.getContent()),
//				ContentWrapper.class);
//
//		PoiBean newsBean = new PoiBean();
//		newsBean.setUuid(info.getUuid());
//		newsBean.setType(info.getType());
//		newsBean.setGroup(String.valueOf(info.getGroup()));
//
//		newsBean.setTypes(wrapper.getTypes());
//		newsBean.setSummary(wrapper.getSummary());
//		newsBean.setIssuer(wrapper.getIssuer());
//		newsBean.setCountry(wrapper.getCountry());
//		newsBean.setFrom(wrapper.getFrom());
//		newsBean.setUrl(wrapper.getUrl());
//
//		matchList.getNews().add(newsBean);
//	}

	private void addPoisBean(List<PoiBean> listPois, it.search.pibisi.pojo.accounts.subjects.find.Info info)
			throws IOException {
		// Crea un'istanza dell'ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// Deserializza il JSON in un oggetto Wrapper
		ContentWrapper wrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(info.getContent()),
				ContentWrapper.class);

		PoiBean newsBean = new PoiBean();
		newsBean.setUuid(info.getUuid());
		newsBean.setType(info.getType());
		newsBean.setGroup(String.valueOf(info.getGroup()));
		newsBean.setContent(info.getContent());
		newsBean.setTypes(wrapper.getTypes());
		newsBean.setSummary(wrapper.getSummary());
		newsBean.setIssuer(wrapper.getIssuer());
		newsBean.setCountry(wrapper.getCountry());
		newsBean.setFrom(wrapper.getFrom());
		newsBean.setUrl(wrapper.getUrl());

		newsBean.setReason(wrapper.getReason());
		newsBean.setProgram(wrapper.getProgram());
		newsBean.setProgramDescription(wrapper.getProgramDescription());
		newsBean.setProgramSource(wrapper.getProgramSource());

		listPois.add(newsBean);
	}

	private void logUnknownInfo(it.search.pibisi.pojo.accounts.subjects.find.Info info) {
		System.out.println("Uuid    --> " + info.getUuid());
		System.out.println("Content --> " + info.getContent());
		System.out.println("Type    --> " + info.getType());
		System.out.println("Class   --> " + info.getClass());
		System.out.println("tGroup  --> " + info.getGroup());
		System.out.println("-----------------------------------");
		System.out.println("ERROR: " + info.getType());
		System.out.println("-----------------------------------");
	}

	private void logUnknownInfo2(it.search.pibisi.pojo.accounts.subjects.find.Info__2 info) {
		System.out.println("Uuid    --> " + info.getUuid());
		System.out.println("Content --> " + info.getContent());
		System.out.println("Type    --> " + info.getType());
		System.out.println("Class   --> " + info.getClass());
		System.out.println("tGroup  --> " + info.getGroup());
		System.out.println("-----------------------------------");
		System.out.println("ERROR: " + info.getType());
		System.out.println("-----------------------------------");
	}
	// End read info in data -> matches

	// Start read subject in data -> matches
	private void readSubjectBean(Subject subject, MatchBean matchBean) throws IOException {
		matchBean.setSubjectBean(new SubjectBean());
		if (subject.getCreatedAt() != null)
			matchBean.getSubjectBean().setCreatedAt(subject.getCreatedAt().getDate());
		if (subject.getUuid() != null)
			matchBean.getSubjectBean().setUuid(subject.getUuid());

		matchBean.getSubjectBean().setSubjectInfoMap(new HashMap<>());
		if (subject.getInfo() != null)
			readSubjectInfo(matchBean.getSubjectBean().getSubjectInfoMap(), subject.getInfo());
	}

	private void readSubjectInfo(HashMap<String, SubjectInfoBean> subjectInfoMap, List<Info__2> infoList)
			throws IOException {
		for (Info__2 info : infoList) {
			SubjectInfoBean subjectInfoBean = new SubjectInfoBean();
			if (info.getContent() != null)
				subjectInfoBean.setContent(info.getContent().toString());
			if (info.getGroup() != null)
				subjectInfoBean.setGroup(info.getGroup().toString());
			subjectInfoBean.setUuid(info.getUuid());
			subjectInfoBean.setType(info.getType());
			subjectInfoBean.setSoiBean(new ArrayList<>());

			if (info.getSois() != null) {
				for (Soi soi : info.getSois()) {
					if (Boolean.TRUE.equals(soi.getActive())) {
						ObjectMapper objectMapper = new ObjectMapper();
						objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						// Deserializza il JSON in un oggetto SoiWrapper
						SoiWrapper soiWrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(soi),
								SoiWrapper.class);

						SoiBean soiBean = new SoiBean();
						BeanUtils.copyProperties(soiWrapper, soiBean);

						subjectInfoBean.getSoiBean().add(soiBean);
					}
				}
				subjectInfoMap.put(info.getType(), subjectInfoBean);
			}
		}
	}
	// End read subject in data -> matches

	private void readDate(Match__1 match, MatchBean matchBean) {
		if (match.getSubject() != null && match.getSubject().getCreatedAt() != null
				&& match.getSubject().getCreatedAt().getDate() != null) {
			matchBean.setCreatedAt(match.getSubject().getCreatedAt().getDate());
		}
	}

	private void readScoringAndCategory(Scoring scoring, MatchBean matchBean) {
		matchBean.setScoring(scoring.getValue());

		if (scoring.getFlags() != null) {
			StringBuilder category = new StringBuilder();

			categoryPep(scoring.getFlags().getIsPep(), scoring.getFlags().getWasPep(),
					scoring.getFlags().getWasPepDate(), matchBean, category);
			categorySanction(scoring.getFlags().getIsSanctioned(), scoring.getFlags().getWasSanctioned(),
					scoring.getFlags().getWasSanctionedDate(), matchBean, category);
			categoryTerrorist(scoring.getFlags().getIsTerrorist(), matchBean, category);
			categoryHasMedia(scoring.getFlags().getHasMedia(), matchBean, category);
			categoryHasAdverseInfo(scoring.getFlags().getHasAdverseInfo(), matchBean, category);
			String highRisk = readHighRisk(scoring.getFlags().getIsHighRisk(), matchBean);

			matchBean.setTypeCategory(category.toString());
			matchBean.setTypeRisk(highRisk);
		}
	}

	private void categoryPep(Boolean isPep, Boolean wasPep, String wasPepDate, MatchBean matchBean,
			StringBuilder category) {
		if (Boolean.TRUE.equals(isPep)) {
			matchBean.setPep(isPep);
			category.append("  PEP");
		} else if (Boolean.TRUE.equals(wasPep)) {
			matchBean.setWasPep(wasPep);
			category.append("  EX_PEP");
			if (StringUtils.hasLength(wasPepDate)) {
				matchBean.setWasPepDate(wasPepDate);
				category.append(" (" + wasPepDate + ")");
			}
		}
	}

	private void categorySanction(Boolean isSanctioned, Boolean wasSanctioned, String wasSanctionedDate,
			MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(isSanctioned)) {
			matchBean.setSanctioned(isSanctioned);
			category.append("  SANTION");
		} else if (Boolean.TRUE.equals(wasSanctioned)) {
			matchBean.setWasSanctioned(wasSanctioned);
			category.append("  EX_SANTION");
			if (StringUtils.hasLength(wasSanctionedDate)) {
				matchBean.setWasSanctionedDate(wasSanctionedDate);
				category.append(" (" + wasSanctionedDate + ")");
			}
		}
	}

	private void categoryTerrorist(Boolean isTerrorist, MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(isTerrorist)) {
			matchBean.setTerrorist(isTerrorist);
			category.append("  TERRORIST");
		}
	}

	private void categoryHasMedia(Boolean hasMedia, MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(hasMedia)) {
			matchBean.setHasMedia(hasMedia);
			category.append("  MEDIA");
		}
	}

	private void categoryHasAdverseInfo(Boolean hasAdverseInfo, MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(hasAdverseInfo)) {
			matchBean.setHasAdverseInfo(hasAdverseInfo);
			category.append("  ADVERSE INFO");
		}
	}

	private String readHighRisk(Boolean isHighRisk, MatchBean matchBean) {
		String highRisk = "Low Risk";
		if (Boolean.TRUE.equals(isHighRisk)) {
			matchBean.setHighRisk(isHighRisk);
			highRisk = "High Risk";
		}
		return highRisk;
	}

	private AccountsSubjectsFindResponse searchMatch(AccountsSearchPojo requestJson) {
		PibisiPojo pibisiPojo = new PibisiPojo();
		pibisiPojo.setAccountId(accountsService.getAccountId());
		pibisiPojo.setType(requestJson.getType());
		pibisiPojo.setContent(requestJson.getContent());
		pibisiPojo.setThreshold(requestJson.getThreshold());
		return accountsService.accountsSubjectsFind(pibisiPojo);
	}

}
