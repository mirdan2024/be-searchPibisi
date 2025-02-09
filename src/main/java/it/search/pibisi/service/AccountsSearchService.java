package it.search.pibisi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.search.pibisi.bean.InfoBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.bean.NameFullBean;
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
			matchList.setInfo(new ArrayList<>());
			matchList.setNews(new ArrayList<>());
			InfoBean infoBean = new InfoBean();

			for (Match__1 match : accountsSubjectsFindResponse.getData().getMatches()) {
				for (Info info : match.getInfo()) {
					switch (info.getType()) {
					case "name.full":
						addNameFullBean(matchList, info);
						break;
					case "person":
						setInfoBean(infoBean, info, "person");
						break;
					case "gender":
						setInfoBean(infoBean, info, "gender");
						break;
					case "birth.date":
						setInfoBean(infoBean, info, "birthDate");
						break;
					case "birth.place":
						setInfoBean(infoBean, info, "birthPlace");
						break;
					case "nationality":
						setInfoBean(infoBean, info, "nationality");
						break;
					case "function":
						setInfoBean(infoBean, info, "function");
						break;
					case "illegal":
						setInfoBean(infoBean, info, "illegal");
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
			matchList.getInfo().add(infoBean);

			return matchList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void addNameFullBean(MatchListBean matchList, Info info) {
		NameFullBean nameFullBean = new NameFullBean();
		setCommonProperties(nameFullBean, info);
		matchList.getNameFull().add(nameFullBean);
	}

	private void addNewsBean(MatchListBean matchList, Info info) {
		it.search.pibisi.bean.NewsBean newsBean = new it.search.pibisi.bean.NewsBean();
		setCommonProperties(newsBean, info);
		newsBean.setContentUrl(String.valueOf(info.getContent()));
		matchList.getNews().add(newsBean);
	}

	private void setInfoBean(InfoBean infoBean, Info info, String fieldName) {
		try {
			// Usa reflection per settare dinamicamente i campi dell'InfoBean
			infoBean.getClass().getMethod("set" + capitalizeFirstLetter(fieldName) + "Uuid", String.class)
					.invoke(infoBean, info.getUuid());
			infoBean.getClass().getMethod("set" + capitalizeFirstLetter(fieldName) + "Content", String.class)
					.invoke(infoBean, String.valueOf(info.getContent()));
			infoBean.getClass().getMethod("set" + capitalizeFirstLetter(fieldName) + "Type", String.class)
					.invoke(infoBean, info.getType());
			infoBean.getClass().getMethod("set" + capitalizeFirstLetter(fieldName) + "Group", String.class)
					.invoke(infoBean, String.valueOf(info.getGroup()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCommonProperties(Object bean, Info info) {
		try {
			bean.getClass().getMethod("setUuid", String.class).invoke(bean, info.getUuid());
			bean.getClass().getMethod("setType", String.class).invoke(bean, info.getType());
			bean.getClass().getMethod("setContent", String.class).invoke(bean, String.valueOf(info.getContent()));
			bean.getClass().getMethod("setGroup", String.class).invoke(bean, String.valueOf(info.getGroup()));
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

	private String capitalizeFirstLetter(String field) {
		return field.substring(0, 1).toUpperCase() + field.substring(1);
	}
}
