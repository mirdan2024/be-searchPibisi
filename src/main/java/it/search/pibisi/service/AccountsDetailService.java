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

import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.SoiBean;
import it.search.pibisi.bean.SubjectBean;
import it.search.pibisi.bean.SubjectInfoBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.AccountsSubjectsResponse;
import it.search.pibisi.pojo.accounts.subjects.Info;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.wrapper.SoiWrapper;

@Service
public class AccountsDetailService extends BaseService {

	@Autowired
	private AccountsService accountsService;

	// Metodo per fare una richiesta di dettaglio
	public MatchBean detail(AccountsSearchPojo requestJson) {
		try {
			return readMatchDetail(detailMatch(requestJson));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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

			matchBean.getSubjectBean().setSubjectInfoMap(new HashMap<>());
			if (accountsSubjectsResponse.getData().getInfo() != null)
				readSubjectInfo(matchBean.getSubjectBean().getSubjectInfoMap(),
						accountsSubjectsResponse.getData().getInfo());
		}
		return matchBean;
	}

	private void readSubjectInfo(HashMap<String, SubjectInfoBean> subjectInfoMap, List<Info> infoList)
			throws IOException {
		for (Info info : infoList) {
			SubjectInfoBean subjectInfoBean = new SubjectInfoBean();
			if (info.getContent() != null)
				subjectInfoBean.setContent(info.getContent().toString());
			if (info.getGroup() != null)
				subjectInfoBean.setGroup(info.getGroup().toString());
			subjectInfoBean.setUuid(info.getUuid());
			subjectInfoBean.setType(info.getType());
			subjectInfoBean.setSoiBean(new ArrayList<>());

			if (info.getSois() != null) {
				for (it.search.pibisi.pojo.accounts.subjects.Soi soi : info.getSois()) {
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

	private void readScoringAndCategory(it.search.pibisi.pojo.accounts.subjects.Scoring scoring, MatchBean matchBean) {
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

	private AccountsSubjectsResponse detailMatch(AccountsSearchPojo requestJson) {
		PibisiPojo pibisiPojo = new PibisiPojo();
		pibisiPojo.setAccountId(accountsService.getAccountId());
		pibisiPojo.setSubjectId(requestJson.getSubjectId());
		return accountsService.accountsSubjects(pibisiPojo);
	}
}
