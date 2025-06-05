package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

import it.common.base.message.MessageService;
import it.common.base.util.JWTUtil;
import it.common.pibisi.bean.MatchBean;
import it.common.pibisi.bean.MatchListBean;
import it.common.pibisi.bean.SoiBean;
import it.common.pibisi.bean.SubjectPoiBean;
import it.common.pibisi.bean.VectorBean;
import it.common.pibisi.bean.VectorFieldBean;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.pojo.customer.matches.CustomersSubjectsResponse;
import it.search.pibisi.pojo.customer.matches.Data;
import it.search.pibisi.pojo.customer.matches.Info;
import it.search.pibisi.pojo.customer.matches.Scoring;
import it.search.pibisi.pojo.customer.matches.Soi;
import it.search.pibisi.pojo.customer.matches.Subject;
import it.search.pibisi.pojo.customer.matches.Vector;
import it.search.pibisi.utils.Category;
import it.search.pibisi.wrapper.SoiWrapper;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomersMatchService {

	private static final Logger logger = LoggerFactory.getLogger(CustomersMatchService.class);

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

	@Value("${api.customers.matches}")
	private String matchesEndpoint;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	public MessageService messageService;


	public MatchListBean matches(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		try {
			CustomersSubjectsResponse customersSubjectsResponse = matchesCustomer(requestJson,map);
			MatchListBean matchListBean = readMatchSearch(customersSubjectsResponse);

			List<String> listCategorie = utilsService.getListaCategorie(request);
			MatchListBean matchListBeanResponse = new MatchListBean();
			matchListBean.getElencoMatch().forEach(e -> {
				if (Boolean.TRUE.equals(utilsService.matchCategory(e, listCategorie))) {
					matchListBeanResponse.addMatchBean(e);
				}
			});

			return matchListBeanResponse;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private CustomersSubjectsResponse matchesCustomer(AccountsSearchPojo requestJson,HashMap<String, String> map) {
		String url = matchesEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
				requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		String body = response.getBody();
		System.out.println("body --> " + body);

		if (body != null && !body.trim().isEmpty()) {
			try {
				return objectMapper.readValue(body, CustomersSubjectsResponse.class);
			} catch (JsonProcessingException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("json.processing.error",map.get("lingua")));
			}
		}
		return null;
	}

	private MatchListBean readMatchSearch(CustomersSubjectsResponse customersSubjectsResponse) throws IOException {
		MatchListBean matchListBean = new MatchListBean();

		// Read tag data
		if (customersSubjectsResponse != null && customersSubjectsResponse.getData() != null
				&& customersSubjectsResponse.getData() != null) {

			// Read tag match
			for (Data data : customersSubjectsResponse.getData()) {

				MatchBean matchBean = new MatchBean();

				if (data.getSubject() != null)
					readSubjectBean(data.getSubject(), matchBean);

				if (data.getVector() != null)
					readVector(data.getVector(), matchBean);

				matchListBean.addMatchBean(matchBean);
			}
		}
		return matchListBean;
	}

	private void readVector(Vector vector, MatchBean matchBean) {
		VectorFieldBean vectorFieldBean;
		VectorBean vectorBean = new VectorBean();

		if (vector.getId() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getId(), vectorFieldBean);
			vectorBean.setId(vectorFieldBean);
		}

		if (vector.getName() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getName(), vectorFieldBean);
			vectorBean.setName(vectorFieldBean);
		}

		if (vector.getPerson() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getPerson(), vectorFieldBean);
			vectorBean.setPerson(vectorFieldBean);
		}

		if (vector.getBirthDate() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getBirthDate(), vectorFieldBean);
			vectorBean.setBirthDate(vectorFieldBean);
		}

		if (vector.getDeathDate() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getDeathDate(), vectorFieldBean);
			vectorBean.setDeathDate(vectorFieldBean);
		}

		if (vector.getLifeIntervalBirth() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getLifeIntervalBirth(), vectorFieldBean);
			vectorBean.setLifeIntervalBirth(vectorFieldBean);
		}

		if (vector.getLifeIntervalDeath() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getLifeIntervalDeath(), vectorFieldBean);
			vectorBean.setLifeIntervalDeath(vectorFieldBean);
		}

		if (vector.getFunctionCurrentScope() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getFunctionCurrentScope(), vectorFieldBean);
			vectorBean.setFunctionCurrentScope(vectorFieldBean);
		}

		if (vector.getFunctionCurrentCountry() != null) {
			vectorFieldBean = new VectorFieldBean();
			BeanUtils.copyProperties(vector.getFunctionCurrentCountry(), vectorFieldBean);
			vectorBean.setFunctionCurrentCountry(vectorFieldBean);
		}

		matchBean.setVector(vectorBean);
	}

	// Start read info in data -> matches
	private void readSubjectBean(Subject subject, MatchBean matchBean) throws IOException {
		if (subject.getCreatedAt() != null)
			matchBean.setCreatedAtDate(subject.getCreatedAt().getDate());
		if (subject.getUuid() != null)
			matchBean.setSubjectUuid(subject.getUuid());

		if (subject.getScoring() != null)
			readScoringAndCategory(subject.getScoring(), matchBean);

		for (Info info : subject.getInfo()) {
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
					if (Boolean.TRUE.equals(soi.getStatus())) {
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
