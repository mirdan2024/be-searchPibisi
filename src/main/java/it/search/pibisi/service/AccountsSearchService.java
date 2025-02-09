package it.search.pibisi.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.search.pibisi.bean.InfoBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.bean.NameFullBean;
import it.search.pibisi.bean.NewsBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.accounts.subjects.find.Info;
import it.search.pibisi.pojo.accounts.subjects.find.Match__1;

@Service
public class AccountsSearchService extends BaseService {

	@Autowired
	private AccountsService accountsService;

	// Metodo per fare una richiesta di ricerca
	public MatchListBean search(AccountsSearchPojo requestJson) {
		try {

			PibisiPojo pibisiPojo = new PibisiPojo();
			pibisiPojo.setAccountId(accountsService.getAccountId());
			pibisiPojo.setType(requestJson.getType());
			pibisiPojo.setContent(requestJson.getContent());
			AccountsSubjectsFindResponse accountsSubjectsFindResponse = accountsService
					.accountsSubjectsFind(pibisiPojo);

			MatchListBean matchList = new MatchListBean();
			matchList.setNameFull(new ArrayList<>());
			matchList.setInfoMap(new HashMap<>());
			matchList.setNews(new ArrayList<>());

			for (Match__1 match : accountsSubjectsFindResponse.getData().getMatches()) {
				for (Info info : match.getInfo()) {
					switch (info.getType()) {
					case "name.full":
						addNameFullBean(matchList, info);
						break;
					case "person", "gender", "birth.date", "birth.place", "nationality", "function", "illegal":
						setMap(matchList, info, info.getType());
						break;
					case "media":
						addNewsBean(matchList, info);
						break;
					default:
						logUnknownInfo(info);
						break;
					}
				}
			}

			return matchList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void addNameFullBean(MatchListBean matchList, Info info) {
		NameFullBean nameFullBean = new NameFullBean();
		nameFullBean.setUuid(info.getUuid());
		nameFullBean.setType(info.getType());
		nameFullBean.setContent(String.valueOf(info.getContent()));
		nameFullBean.setGroup(String.valueOf(info.getGroup()));
		matchList.getNameFull().add(nameFullBean);
	}

	private void addNewsBean(MatchListBean matchList, Info info) {
		NewsBean newsBean = new NewsBean();
		newsBean.setUuid(info.getUuid());
		newsBean.setType(info.getType());
		newsBean.setContent(String.valueOf(info.getContent()));
		newsBean.setGroup(String.valueOf(info.getGroup()));
		matchList.getNews().add(newsBean);
	}

	private void setMap(MatchListBean matchList, Info info, String fieldName) {
		try {
			InfoBean infoBean = new InfoBean();
			infoBean.setUuid(info.getUuid());
			infoBean.setType(info.getType());
			infoBean.setContent(String.valueOf(info.getContent()));
			infoBean.setGroup(String.valueOf(info.getGroup()));

			matchList.getInfoMap().put(fieldName, infoBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
