package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.util.JWTUtil;
import it.common.pibisi.bean.MatchBean;
import it.common.pibisi.bean.SoiBean;
import it.common.pibisi.bean.SubjectPoiBean;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.pojo.accounts.subjects.AccountsSubjectsResponse;
import it.search.pibisi.pojo.accounts.subjects.Data;
import it.search.pibisi.pojo.accounts.subjects.Info;
import it.search.pibisi.pojo.accounts.subjects.Soi;
import it.search.pibisi.utils.Category;
import it.search.pibisi.wrapper.SoiWrapper;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AccountsDetailService {

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private JWTUtil jwtUtil;

	// Metodo per fare una richiesta di dettaglio
	public MatchBean detail(AccountsSearchPojo requestJson, HttpServletRequest request) {
		try {
			HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
			requestJson.setAccountId(map.get("accountId"));
			return readMatchDetail(accountsService.accountsSubjects(requestJson));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private MatchBean readMatchDetail(AccountsSubjectsResponse accountsSubjectsResponse) throws IOException {
		MatchBean matchBean = new MatchBean();

		if (accountsSubjectsResponse != null && accountsSubjectsResponse.getData() != null) {
			if (accountsSubjectsResponse.getData().getScoring() != null)
				readScoringAndCategory(accountsSubjectsResponse.getData().getScoring(), matchBean);

			if (accountsSubjectsResponse.getData().getInfo() != null)
				readSubjectBean(accountsSubjectsResponse.getData(), matchBean);
		}
		return matchBean;
	}

	// Start read info in data -> matches
	private void readSubjectBean(Data data, MatchBean matchBean) throws IOException {
		if (data.getCreatedAt() != null)
			matchBean.setCreatedAtDate(data.getCreatedAt());
		if (data.getUuid() != null)
			matchBean.setSubjectUuid(data.getUuid());

		for (Info info : data.getInfo()) {
			SubjectPoiBean subjectPoiBean = new SubjectPoiBean();
			if (info.getContent() != null)
				subjectPoiBean.setContent(info.getContent());
			if (info.getGroup() != null)
				subjectPoiBean.setGroup(info.getGroup().toString());
			subjectPoiBean.setUuid(info.getUuid());
			subjectPoiBean.setType(info.getType());
			subjectPoiBean.setSoiBean(new ArrayList<>());

			if (info.getSois() != null) {
				for (Soi soi : info.getSois()) {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					// Deserializza il JSON in un oggetto SoiWrapper
					SoiWrapper soiWrapper = objectMapper.readValue(objectMapper.writeValueAsBytes(soi),
							SoiWrapper.class);

					SoiBean soiBean = new SoiBean();
					BeanUtils.copyProperties(soiWrapper, soiBean);

					subjectPoiBean.getSoiBean().add(soiBean);
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
					logUnknownInfo(info);
					break;
				}
			}
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
	// End read info in data -> matches

	private void readScoringAndCategory(it.search.pibisi.pojo.accounts.subjects.Scoring scoring, MatchBean matchBean) {
		matchBean.setScoring(scoring.getValue());

		if (scoring.getFlags() != null) {
			StringBuilder category = new StringBuilder();

			Category.categoryPep(scoring.getFlags().getIsPep(), scoring.getFlags().getWasPep(),
					scoring.getFlags().getWasPepDate(), matchBean, category);
			Category.categorySanction(scoring.getFlags().getIsSanctioned(), scoring.getFlags().getWasSanctioned(),
					scoring.getFlags().getWasSanctionedDate(), matchBean, category);
			Category.categoryTerrorist(scoring.getFlags().getIsTerrorist(), matchBean, category);
			Category.categoryHasMedia(scoring.getFlags().getHasMedia(), matchBean, category);
			Category.categoryHasAdverseInfo(scoring.getFlags().getHasAdverseInfo(), matchBean, category);
			String highRisk = Category.readHighRisk(scoring.getFlags().getIsHighRisk(), matchBean);

			matchBean.setTypeCategory(category.toString());
			matchBean.setTypeRisk(highRisk);
		}
	}

}
