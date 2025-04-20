package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.ListaCategorieGruppoPojo;
import it.common.base.util.JWTUtil;
import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.bean.SoiBean;
import it.search.pibisi.bean.SubjectPoiBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.accounts.subjects.find.Info__2;
import it.search.pibisi.pojo.accounts.subjects.find.Match__1;
import it.search.pibisi.pojo.accounts.subjects.find.Scoring;
import it.search.pibisi.pojo.accounts.subjects.find.Soi;
import it.search.pibisi.pojo.accounts.subjects.find.Subject;
import it.search.pibisi.utils.Category;
import it.search.pibisi.wrapper.SoiWrapper;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AccountsSearchService {

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private JWTUtil jwtUtil;

	// Metodo per fare una richiesta di ricerca
	public MatchListBean search(AccountsSearchPojo requestJson, HttpServletRequest request) {

		try {
			HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
			ListaCategorieGruppoPojo lcgp = utilsService.callGetListaCategorie(requestJson, request,
					map.get("idIntermediario"));
			List<String> listCategorie = new ArrayList<>();
			lcgp.getCategorieGruppoPojo().forEach(e -> {
				e.getListaCategorie().forEach(c -> {
					listCategorie.add(c.getCategoria());
				});
			});

			// Esegue una query per trovare possibili corrispondenze.
			requestJson.setAccountId(map.get("accountId"));
			MatchListBean matchListBean = readMatchSearch(accountsService.accountsSubjectsFind(requestJson));

			// Esegue una query per trovare possibili corrispondenze solo con gli elenchi di
			// sanzioni e terroristi.
//			 MatchListBean matchListBean =
//			 readMatchSearch(accountsService.accountsSubjectsFindBlocked(requestJson));

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

				if (match.getScoring() != null)
					readScoringAndCategory(match.getScoring(), matchBean);

				if (match.getSubject() != null)
					readSubjectBean(match.getSubject(), matchBean);

				matchListBean.addMatchBean(matchBean);
			}
		}
		return matchListBean;
	}

	// Start read info in data -> matches
	private void readSubjectBean(Subject subject, MatchBean matchBean) throws IOException {
		if (subject.getCreatedAt() != null)
			matchBean.setCreatedAtDate(subject.getCreatedAt().getDate());
		if (subject.getUuid() != null)
			matchBean.setSubjectUuid(subject.getUuid());

		for (Info__2 info : subject.getInfo()) {
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
					logUnknownInfo(info);
					break;
				}
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
	// End read info in data -> matches

	private void readScoringAndCategory(Scoring scoring, MatchBean matchBean) {
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
