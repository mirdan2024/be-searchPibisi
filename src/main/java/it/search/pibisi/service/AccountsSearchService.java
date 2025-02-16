package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.search.pibisi.bean.InfoBean;
import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.bean.NameFullBean;
import it.search.pibisi.bean.NewsBean;
import it.search.pibisi.bean.SoiBean;
import it.search.pibisi.bean.SubjectBean;
import it.search.pibisi.bean.SubjectInfoBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.AccountsSubjectsResponse;
import it.search.pibisi.pojo.accounts.subjects.Info;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.accounts.subjects.find.Info__2;
import it.search.pibisi.pojo.accounts.subjects.find.Match__1;
import it.search.pibisi.pojo.accounts.subjects.find.Scoring;
import it.search.pibisi.pojo.accounts.subjects.find.Soi;
import it.search.pibisi.wrapper.ContentWrapper;
import it.search.pibisi.wrapper.SoiWrapper;

@Service
public class AccountsSearchService extends BaseService {

	@Autowired
	private AccountsService accountsService;

	// Metodo per fare una richiesta di ricerca
	public MatchListBean search(AccountsSearchPojo requestJson) {

		try {

			MatchListBean matchListBean = readMatchSearch(searchMatch(requestJson));

			return matchListBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per fare una richiesta di dettaglio
	public MatchBean detail(AccountsSearchPojo requestJson) {

		try {

			MatchBean matchBean = readMatchDetail(detailMatch(requestJson));

			return matchBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private MatchListBean readMatchSearch(AccountsSubjectsFindResponse accountsSubjectsFindResponse)
			throws IOException {
		MatchListBean matchListBean = new MatchListBean();

		if (accountsSubjectsFindResponse != null && accountsSubjectsFindResponse.getData() != null
				&& accountsSubjectsFindResponse.getData().getMatches() != null
				&& accountsSubjectsFindResponse.getData().getMatches() != null) {
			for (Match__1 match : accountsSubjectsFindResponse.getData().getMatches()) {

				MatchBean matchBean = new MatchBean();
				matchBean.setNameFull(new ArrayList<>());
				matchBean.setInfoMap(new HashMap<>());
				matchBean.setNews(new ArrayList<>());

				if (match.getScoring() != null)
					readScoringAndCategory(match.getScoring(), matchBean);
				readInfo(match, matchBean);
				readDate(match, matchBean);

				matchListBean.addMatchBean(matchBean);
			}
		}
		return matchListBean;
	}

	private MatchBean readMatchDetail(AccountsSubjectsResponse accountsSubjectsResponse) throws IOException {
		MatchBean matchBean = new MatchBean();
		matchBean.setNameFull(new ArrayList<>());
		matchBean.setInfoMap(new HashMap<>());
		matchBean.setNews(new ArrayList<>());
		matchBean.setSubjectBean(new SubjectBean());

		if (accountsSubjectsResponse != null && accountsSubjectsResponse.getData() != null) {
			if (accountsSubjectsResponse.getData().getScoring() != null)
				readScoringAndCategory(accountsSubjectsResponse.getData().getScoring(), matchBean);

			for (Info info : accountsSubjectsResponse.getData().getInfo()) {
				readSubjectInfo(matchBean.getSubjectBean(), info);
			}
		}
		return matchBean;
	}

	private void readDate(Match__1 match, MatchBean matchBean) {
		if (match.getSubject() != null && match.getSubject().getCreatedAt() != null
				&& match.getSubject().getCreatedAt().getDate() != null) {
			matchBean.setCreatedAt(match.getSubject().getCreatedAt().getDate());
		}
	}

	private void readInfo(Match__1 match, MatchBean matchBean) throws IOException {
		setSubjectBean(match, matchBean);

		for (Info__2 info : match.getSubject().getInfo()) {
			switch (info.getType()) {
			case "name.full":
				addNameFullBean(matchBean, info);
				break;
			case "person", "gender", "birth.date", "birth.place", "nationality", "function", "illegal", "id.platform",
					"name.first", "name.last", "sanction", "id.passport", "photo", "function.public":
				setMapInfo(matchBean, info, info.getType());
				break;
			case "media":
				addNewsBean(matchBean, info);
				break;
			default:
				logUnknownInfo(info);
				break;
			}
		}
	}

	private void setSubjectBean(Match__1 match, MatchBean matchBean) {
		matchBean.setSubjectBean(new SubjectBean());
		if (match != null && match.getSubject() != null && match.getSubject().getCreatedAt() != null)
			matchBean.getSubjectBean().setCreatedAt(match.getSubject().getCreatedAt().getDate());
		if (match != null && match.getSubject() != null && match.getSubject().getUuid() != null)
			matchBean.getSubjectBean().setUuid(match.getSubject().getUuid());
	}

	private void readScoringAndCategory(Scoring scoring, MatchBean matchBean) {
		matchBean.setScoring(scoring.getValue());

		if (scoring.getFlags() != null) {
			StringBuilder category = new StringBuilder();
			String highRisk = "Low Risk";

			if (Boolean.TRUE.equals(scoring.getFlags().getIsPep())) {
				matchBean.setPep(scoring.getFlags().getIsPep());
				category.append("  PEP");
			} else if (Boolean.TRUE.equals(scoring.getFlags().getWasPep())) {
				matchBean.setWasPep(scoring.getFlags().getWasPep());
				category.append("  EX_PEP");
				if (StringUtils.hasLength(scoring.getFlags().getWasPepDate())) {
					matchBean.setWasPepDate(scoring.getFlags().getWasPepDate().toString());
					category.append(" (" + scoring.getFlags().getWasPepDate().toString() + ")");
				}
			}

			if (Boolean.TRUE.equals(scoring.getFlags().getIsSanctioned())) {
				matchBean.setSanctioned(scoring.getFlags().getIsSanctioned());
				category.append("  SANTION");
			} else if (Boolean.TRUE.equals(scoring.getFlags().getWasSanctioned())) {
				matchBean.setWasSanctioned(scoring.getFlags().getWasSanctioned());
				category.append("  EX_SANTION");
				if (StringUtils.hasLength(scoring.getFlags().getWasSanctionedDate())) {
					category.append(" (" + scoring.getFlags().getWasSanctionedDate().toString() + ")");
				}
			}

			if (Boolean.TRUE.equals(scoring.getFlags().getIsTerrorist())) {
				matchBean.setTerrorist(scoring.getFlags().getIsTerrorist());
				category.append("  TERRORIST");
			}

			if (Boolean.TRUE.equals(scoring.getFlags().getHasMedia())) {
				matchBean.setHasMedia(scoring.getFlags().getHasMedia());
				category.append("  MEDIA");
			}

			if (Boolean.TRUE.equals(scoring.getFlags().getHasAdverseInfo())) {
				matchBean.setHasAdverseInfo(scoring.getFlags().getHasAdverseInfo());
				category.append("  ADVERSE INFO");
			}

			if (Boolean.TRUE.equals(scoring.getFlags().getIsHighRisk())) {
				matchBean.setHighRisk(scoring.getFlags().getIsHighRisk());
				highRisk = "High Risk";
			}

			matchBean.setTypeCategory(category.toString());
			matchBean.setTypeRisk(highRisk);
		}
	}

	private void readScoringAndCategory(it.search.pibisi.pojo.accounts.subjects.Scoring scoring, MatchBean matchBean) {
		matchBean.setScoring(scoring.getValue());

		if (scoring.getFlags() != null) {
			matchBean.setPep(scoring.getFlags().getIsPep());
			matchBean.setWasPep(scoring.getFlags().getWasPep());
			if (scoring.getFlags().getWasPepDate() != null)
				matchBean.setWasPepDate(scoring.getFlags().getWasPepDate().toString());
			matchBean.setSanctioned(scoring.getFlags().getIsSanctioned());
			matchBean.setWasSanctioned(scoring.getFlags().getWasSanctioned());
			matchBean.setWasSanctionedDate(scoring.getFlags().getWasSanctionedDate());
			matchBean.setTerrorist(scoring.getFlags().getIsTerrorist());
			matchBean.setHasMedia(scoring.getFlags().getHasMedia());
			matchBean.setHasAdverseInfo(scoring.getFlags().getHasAdverseInfo());
			matchBean.setHighRisk(scoring.getFlags().getIsHighRisk());
		}
	}

	private AccountsSubjectsFindResponse searchMatch(AccountsSearchPojo requestJson) {
		PibisiPojo pibisiPojo = new PibisiPojo();
		pibisiPojo.setAccountId(accountsService.getAccountId());
		pibisiPojo.setType(requestJson.getType());
		pibisiPojo.setContent(requestJson.getContent());
		pibisiPojo.setThreshold(requestJson.getThreshold());
		return accountsService.accountsSubjectsFind(pibisiPojo);
	}

	private AccountsSubjectsResponse detailMatch(AccountsSearchPojo requestJson) {
		PibisiPojo pibisiPojo = new PibisiPojo();
		pibisiPojo.setAccountId(accountsService.getAccountId());
		pibisiPojo.setSubjectId(requestJson.getSubjectId());
		return accountsService.accountsSubjects(pibisiPojo);
	}

	private void addNameFullBean(MatchBean matchList, Info__2 info) {
		NameFullBean nameFullBean = new NameFullBean();
		nameFullBean.setUuid(info.getUuid());
		nameFullBean.setType(info.getType());
		nameFullBean.setContent(String.valueOf(info.getContent()));
		nameFullBean.setGroup(String.valueOf(info.getGroup()));
		matchList.getNameFull().add(nameFullBean);
	}

	private void addNewsBean(MatchBean matchList, Info__2 info) throws IOException {
		// Crea un'istanza dell'ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// Deserializza il JSON in un oggetto Wrapper
		ContentWrapper wrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(info.getContent()),
				ContentWrapper.class);

		NewsBean newsBean = new NewsBean();
		newsBean.setUuid(info.getUuid());
		newsBean.setType(info.getType());
		newsBean.setGroup(String.valueOf(info.getGroup()));

		newsBean.setTypes(wrapper.getTypes());
		newsBean.setSummary(wrapper.getSummary());
		newsBean.setIssuer(wrapper.getIssuer());
		newsBean.setCountry(wrapper.getCountry());
		newsBean.setFrom(wrapper.getFrom());
		newsBean.setUrl(wrapper.getUrl());

		matchList.getNews().add(newsBean);
	}

	private void setMapInfo(MatchBean matchList, Info__2 info, String fieldName) throws IOException {
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

		setSubjectInfo2Bean(matchList, info);

		matchList.getInfoMap().put(fieldName, infoBean);
	}

	private void setSubjectInfo2Bean(MatchBean matchList, Info__2 info) throws IOException {
		matchList.getSubjectBean().setSubjectInfoBean(new ArrayList<>());
		if (info.getSois() != null) {
			for (Soi soi : info.getSois()) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				// Deserializza il JSON in un oggetto SoiWrapper
				SoiWrapper soiWrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(soi), SoiWrapper.class);

				SoiBean soiBean = new SoiBean();
				BeanUtils.copyProperties(soiWrapper, soiBean);

				SubjectInfoBean subjectInfoBean = new SubjectInfoBean();
				if (info.getContent() != null)
					subjectInfoBean.setContent(info.getContent().toString());
				if (info.getGroup() != null)
					subjectInfoBean.setGroup(info.getGroup().toString());
				subjectInfoBean.setUuid(info.getUuid());
				subjectInfoBean.setType(info.getType());
				subjectInfoBean.setSoiBean(soiBean);
				matchList.getSubjectBean().getSubjectInfoBean().add(subjectInfoBean);
			}
		}
	}

	private void readSubjectInfo(SubjectBean subjectBean, Info info) throws IOException {
		subjectBean.setSubjectInfoBean(new ArrayList<>());
		if (info.getSois() != null) {
			for (it.search.pibisi.pojo.accounts.subjects.Soi soi : info.getSois()) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				// Deserializza il JSON in un oggetto SoiWrapper
				SoiWrapper soiWrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(soi), SoiWrapper.class);

				SoiBean soiBean = new SoiBean();
				BeanUtils.copyProperties(soiWrapper, soiBean);

				SubjectInfoBean subjectInfoBean = new SubjectInfoBean();
				if (info.getContent() != null)
					subjectInfoBean.setContent(info.getContent().toString());
				if (info.getGroup() != null)
					subjectInfoBean.setGroup(info.getGroup().toString());
				subjectInfoBean.setUuid(info.getUuid());
				subjectInfoBean.setType(info.getType());
				subjectInfoBean.setSoiBean(soiBean);
				subjectBean.getSubjectInfoBean().add(subjectInfoBean);
			}
		}
	}

	private void logUnknownInfo(Info__2 info) {
		System.out.println("Uuid    --> " + info.getUuid());
		System.out.println("Content --> " + info.getContent());
		System.out.println("Type    --> " + info.getType());
		System.out.println("Class   --> " + info.getClass());
		System.out.println("tGroup  --> " + info.getGroup());
		System.out.println("-----------------------------------");
		System.out.println("ERROR: " + info.getType());
		System.out.println("-----------------------------------");
	}

}
