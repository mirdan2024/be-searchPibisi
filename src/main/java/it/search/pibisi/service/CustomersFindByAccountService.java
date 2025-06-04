package it.search.pibisi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.util.JWTUtil;
import it.common.pibisi.bean.MatchBean;
import it.common.pibisi.bean.MatchListBean;
import it.common.pibisi.bean.SubjectPoiBean;
import it.search.pibisi.pojo.customer.find.byaccount.CustomersFindByAccountResponse;
import it.search.pibisi.pojo.customer.find.byaccount.DataItem;
import it.search.pibisi.pojo.customer.find.byaccount.InfoItem;
import it.search.pibisi.pojo.customer.find.byaccount.Scoring;
import it.search.pibisi.utils.Category;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomersFindByAccountService {

	private static final Logger logger = LoggerFactory.getLogger(CustomersFindByAccountService.class);

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

	@Value("${api.customers.get}")
	private String getCustomersEndpoint;

	@Autowired
	private JWTUtil jwtUtil;

	private RestTemplate restTemplate = new RestTemplate();

	private final ResourceBundle bundle = ResourceBundle.getBundle("bundle.messages-errors");

	// Metodo GET per ottenere i clienti di un account
	public MatchListBean findByAccount(HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);

		String url = getCustomersEndpoint.replace("{accountId}", map.get("accountId"));

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		String body = response.getBody();
		System.out.println("body --> " + body);

		if (body != null && !body.trim().isEmpty()) {
			try {
				return readMatchSearch(objectMapper.readValue(body, CustomersFindByAccountResponse.class));
			} catch (JsonProcessingException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("json.processing.error"));
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("io.exception.error"));
			}
		}
		return null;
	}

	private MatchListBean readMatchSearch(CustomersFindByAccountResponse customersFindByAccountResponse)
			throws IOException {
		MatchListBean matchListBean = new MatchListBean();

		// Read tag data
		if (customersFindByAccountResponse != null && customersFindByAccountResponse.getData() != null
				&& customersFindByAccountResponse.getData() != null) {

			// Read tag match
			for (DataItem dataItem : customersFindByAccountResponse.getData()) {

				MatchBean matchBean = new MatchBean();

				matchBean.setSubjectUuid(dataItem.getUuid());
				matchBean.setStatus(dataItem.getStatus());
				matchBean.setRisk(dataItem.getRisk());
				matchBean.setGroup(dataItem.getGroup());

				matchBean.setCreatedAtDate(dataItem.getExpiresAt());
				matchBean.setCreatedAtDate(dataItem.getCreatedAt());
				matchBean.setCreatedAtDate(dataItem.getRemovedAt());

				if (dataItem.getInfo() != null)
					readInfoBean(dataItem.getInfo(), matchBean);

				if (dataItem.getScoring() != null)
					readScoringAndCategory(dataItem.getScoring(), matchBean);

				matchListBean.addMatchBean(matchBean);
			}
		}
		return matchListBean;
	}

	// Start read info in data -> matches
	private void readInfoBean(List<InfoItem> infoItemList, MatchBean matchBean) throws IOException {

		for (InfoItem infoItem : infoItemList) {
			SubjectPoiBean subjectPoiBean = new SubjectPoiBean();
			if (infoItem.getContent() != null)
				subjectPoiBean.setContent(infoItem.getContent());
			if (infoItem.getGroup() != null)
				subjectPoiBean.setGroup(infoItem.getGroup().toString());
			subjectPoiBean.setUuid(infoItem.getUuid());
			subjectPoiBean.setType(infoItem.getType());

			switch (infoItem.getType()) {
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
				logUnknownInfo(infoItem);
				break;
			}
		}
	}

	private void logUnknownInfo(InfoItem infoItem) {
		System.out.println("Uuid    --> " + infoItem.getUuid());
		System.out.println("Content --> " + infoItem.getContent());
		System.out.println("Type    --> " + infoItem.getType());
		System.out.println("Class   --> " + infoItem.getClass());
		System.out.println("tGroup  --> " + infoItem.getGroup());
		System.out.println("-----------------------------------");
		System.out.println("ERROR: " + infoItem.getType());
		System.out.println("-----------------------------------");
	}
	// End read info in data -> matches

	private void readScoringAndCategory(Scoring scoring, MatchBean matchBean) {
		matchBean.setScoring(scoring.getValue());

		if (scoring.getFlags() != null) {
			StringBuilder category = new StringBuilder();

			Category.categoryPep(scoring.getFlags().isPep(), scoring.getFlags().isWasPep(),
					scoring.getFlags().getWasPepDate(), matchBean, category);
			Category.categorySanction(scoring.getFlags().isSanctioned(), scoring.getFlags().isWasSanctioned(),
					scoring.getFlags().getWasSanctionedDate(), matchBean, category);
			Category.categoryTerrorist(scoring.getFlags().isTerrorist(), matchBean, category);
			Category.categoryHasMedia(scoring.getFlags().isHasMedia(), matchBean, category);
			Category.categoryHasAdverseInfo(scoring.getFlags().isHasAdverseInfo(), matchBean, category);
			String highRisk = Category.readHighRisk(scoring.getFlags().isHighRisk(), matchBean);

			matchBean.setTypeCategory(category.toString());
			matchBean.setTypeRisk(highRisk);
		}
	}
}
