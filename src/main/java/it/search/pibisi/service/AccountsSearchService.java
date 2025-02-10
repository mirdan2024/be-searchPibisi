package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.search.pibisi.bean.InfoBean;
import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.bean.NameFullBean;
import it.search.pibisi.bean.NewsBean;
import it.search.pibisi.bean.SoiBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.accounts.subjects.find.Info__2;
import it.search.pibisi.pojo.accounts.subjects.find.Match__1;
import it.search.pibisi.wrapper.ContentWrapper;
import it.search.pibisi.wrapper.SoiWrapper;

@Service
public class AccountsSearchService extends BaseService {

	@Autowired
	private AccountsService accountsService;

	// Metodo per fare una richiesta di ricerca
	public MatchListBean search(AccountsSearchPojo requestJson) {

		try {

			MatchListBean matchListBean = readMatch(searchMatch(requestJson));

			return matchListBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private MatchListBean readMatch(AccountsSubjectsFindResponse accountsSubjectsFindResponse) throws IOException {
		MatchListBean matchListBean = new MatchListBean();

		if (accountsSubjectsFindResponse != null && accountsSubjectsFindResponse.getData() != null
				&& accountsSubjectsFindResponse.getData().getMatches() != null
				&& accountsSubjectsFindResponse.getData().getMatches() != null) {
			for (Match__1 match : accountsSubjectsFindResponse.getData().getMatches()) {

				MatchBean matchBean = new MatchBean();
				matchBean.setNameFull(new ArrayList<>());
				matchBean.setInfoMap(new HashMap<>());
				matchBean.setNews(new ArrayList<>());

				readScoringAndCategory(match, matchBean);
				readInfo(match, matchBean);
				readDate(match, matchBean);

				matchListBean.addMatchBean(matchBean);
			}
		}
		return matchListBean;
	}

	private void readDate(Match__1 match, MatchBean matchBean) {
		if (match.getSubject() != null && match.getSubject().getCreatedAt() != null
				&& match.getSubject().getCreatedAt().getDate() != null) {
			matchBean.setCreatedAt(match.getSubject().getCreatedAt().getDate());
		}
	}

	private void readInfo(Match__1 match, MatchBean matchBean) throws IOException {
		for (Info__2 info : match.getSubject().getInfo()) {
			switch (info.getType()) {
			case "name.full":
				addNameFullBean(matchBean, info);
				break;
			case "person", "gender", "birth.date", "birth.place", "nationality", "function", "illegal", "id.platform",
					"name.first", "name.last", "sanction", "id.passport", "photo", "function.public":
				setMap(matchBean, info, info.getType());
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

	private void readScoringAndCategory(Match__1 match, MatchBean matchBean) {
		if (match.getScoring() != null) {
			matchBean.setScoring(match.getScoring().getValue());

			if (match.getScoring().getFlags() != null) {
				matchBean.setPep(match.getScoring().getFlags().getIsPep());
				matchBean.setWasPep(match.getScoring().getFlags().getWasPep());
				matchBean.setWasPepDate(match.getScoring().getFlags().getWasPepDate());
				matchBean.setSanctioned(match.getScoring().getFlags().getIsSanctioned());
				matchBean.setWasSanctioned(match.getScoring().getFlags().getWasSanctioned());
				matchBean.setWasSanctionedDate(match.getScoring().getFlags().getWasSanctionedDate());
				matchBean.setTerrorist(match.getScoring().getFlags().getIsTerrorist());
				matchBean.setHasMedia(match.getScoring().getFlags().getHasMedia());
				matchBean.setHasAdverseInfo(match.getScoring().getFlags().getHasAdverseInfo());
				matchBean.setHighRisk(match.getScoring().getFlags().getIsHighRisk());
			}
		}
	}

	private AccountsSubjectsFindResponse searchMatch(AccountsSearchPojo requestJson) {
		PibisiPojo pibisiPojo = new PibisiPojo();
		pibisiPojo.setAccountId(accountsService.getAccountId());
		pibisiPojo.setType(requestJson.getType());
		pibisiPojo.setContent(requestJson.getContent());
		return accountsService.accountsSubjectsFind(pibisiPojo);
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

	private void setMap(MatchBean matchList, Info__2 info, String fieldName)
			throws StreamReadException, DatabindException, JsonProcessingException, IOException {
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

//		if (info.getSois() != null) {
//			ObjectMapper objectMapper = new ObjectMapper();
//			// Deserializza il JSON in un oggetto SoiWrapper
//			SoiWrapper soiWrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(info.getSois().get(0)),
//					SoiWrapper.class);
//
//			SoiBean soiBean = new SoiBean();
//			BeanUtils.copyProperties(soiWrapper, soiBean);
//		}

		matchList.getInfoMap().put(fieldName, infoBean);
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
