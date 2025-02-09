package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.accounts.subjects.find.Info;
import it.search.pibisi.pojo.accounts.subjects.find.Match__1;
import it.search.pibisi.wrapper.ContentWrapper;

@Service
public class AccountsSearchService extends BaseService {

	@Autowired
	private AccountsService accountsService;

	// Metodo per fare una richiesta di ricerca
	public MatchListBean search(AccountsSearchPojo requestJson) {
		MatchListBean matchListBean = new MatchListBean();
		try {

			PibisiPojo pibisiPojo = new PibisiPojo();
			pibisiPojo.setAccountId(accountsService.getAccountId());
			pibisiPojo.setType(requestJson.getType());
			pibisiPojo.setContent(requestJson.getContent());
			AccountsSubjectsFindResponse accountsSubjectsFindResponse = accountsService
					.accountsSubjectsFind(pibisiPojo);

			if (accountsSubjectsFindResponse != null && accountsSubjectsFindResponse.getData() != null
					&& accountsSubjectsFindResponse.getData().getMatches() != null
					&& accountsSubjectsFindResponse.getData().getMatches() != null) {
				for (Match__1 match : accountsSubjectsFindResponse.getData().getMatches()) {

					MatchBean matchBean = new MatchBean();
					matchBean.setNameFull(new ArrayList<>());
					matchBean.setInfoMap(new HashMap<>());
					matchBean.setNews(new ArrayList<>());

					for (Info info : match.getInfo()) {
						switch (info.getType()) {
						case "name.full":
							addNameFullBean(matchBean, info);
							break;
						case "person", "gender", "birth.date", "birth.place", "nationality", "function", "illegal",
								"id.platform", "name.first", "name.last", "sanction", "id.passport", "photo",
								"function.public":
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

					matchListBean.addMatchBean(matchBean);
				}
			}
			return matchListBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void addNameFullBean(MatchBean matchList, Info info) {
		NameFullBean nameFullBean = new NameFullBean();
		nameFullBean.setUuid(info.getUuid());
		nameFullBean.setType(info.getType());
		nameFullBean.setContent(String.valueOf(info.getContent()));
		nameFullBean.setGroup(String.valueOf(info.getGroup()));
		matchList.getNameFull().add(nameFullBean);
	}

	private void addNewsBean(MatchBean matchList, Info info)
			throws StreamReadException, DatabindException, JsonProcessingException, IOException {
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

	private void setMap(MatchBean matchList, Info info, String fieldName)
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

			if ("birth.place".equals(fieldName)) {
				infoBean.setCity(wrapper.getCity());
				infoBean.setCountry(wrapper.getCountry());
			} else if ("function".equals(fieldName)) {
				infoBean.setCharge(wrapper.getCharge());
				infoBean.setOrganization(wrapper.getOrganization());
			} else if ("sanction".equals(fieldName)) {
				infoBean.setReason(wrapper.getReason());
				infoBean.setIssuer(wrapper.getIssuer());
				infoBean.setProgram(wrapper.getProgram());
				infoBean.setProgramDescription(wrapper.getProgramDescription());
				infoBean.setProgramSource(wrapper.getProgramSource());
				infoBean.setTypes(wrapper.getTypes());
			} else if ("id.passport".equals(fieldName)) {
				infoBean.setNumber(wrapper.getNumber());
				infoBean.setCountry(wrapper.getCountry());
			} else if ("id.platform".equals(fieldName)) {
				infoBean.setNumber(wrapper.getNumber());
				infoBean.setPlatform(wrapper.getPlatform());
			} else if ("photo".equals(fieldName)) {
				infoBean.setUrl(wrapper.getUrl());
			} else if ("function.public".equals(fieldName)) {
				infoBean.setCharge(wrapper.getCharge());
				infoBean.setOrganization(wrapper.getOrganization());
				infoBean.setFrom(wrapper.getFrom());
				infoBean.setTo(wrapper.getTo());
				infoBean.setCountry(wrapper.getCountry());
			}
		}

		matchList.getInfoMap().put(fieldName, infoBean);
	}

	private void logUnknownInfo(Info info) {
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
