package it.search.pibisi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.message.MessageService;
import it.common.base.util.JWTUtil;
import it.common.pibisi.bean.MatchBean;
import it.common.pibisi.bean.SoiBean;
import it.common.pibisi.bean.SubjectPoiBean;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.pojo.customer.find.bycustomer.CustomersFindByCustomerResponse;
import it.search.pibisi.pojo.customer.find.bycustomer.Data;
import it.search.pibisi.pojo.customer.find.bycustomer.Info;
import it.search.pibisi.pojo.customer.find.bycustomer.Scoring;
import it.search.pibisi.pojo.customer.find.bycustomer.Soi;
import it.search.pibisi.utils.Category;
import it.search.pibisi.wrapper.SoiWrapper;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomersFindByCustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomersFindByCustomerService.class);

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

	@Value("${api.customers.customers}")
	private String customersEndpoint;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	public MessageService messageService;

	public MatchBean findByCustomer(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		String body = callFindByCustomer(requestJson, map);
		if (body != null && !body.trim().isEmpty()) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return readMatchSearch(objectMapper.readValue(body, CustomersFindByCustomerResponse.class));
			} catch (JsonMappingException e) {
				logger.error(messageService.get("json.mapping.error", map.get("lingua")), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						messageService.get("json.mapping.error", map.get("lingua")));
			} catch (JsonProcessingException e) {
				logger.error(messageService.get("json.processing.error", map.get("lingua")), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						messageService.get("json.processing.error", map.get("lingua")));
			} catch (IOException e) {
				logger.error(messageService.get("json.processing.error", map.get("lingua")), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						messageService.get("json.processing.error", map.get("lingua")));
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					messageService.get("error.monitoring", map.get("lingua")));
		}
	}

	public String callFindByCustomer(AccountsSearchPojo requestJson, HashMap<String, String> map) {
		requestJson.setAccountId(map.get("accountId"));

		String url = customersEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
				requestJson.getSubjectId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}

	private MatchBean readMatchSearch(CustomersFindByCustomerResponse customersFindByCustomerResponse)
			throws IOException {

		MatchBean matchBean = new MatchBean();
		Data data = customersFindByCustomerResponse.getData();
		if (data.getCreatedAt() != null)
			matchBean.setCreatedAtDate(data.getCreatedAt());
		if (data.getUuid() != null)
			matchBean.setSubjectUuid(data.getUuid());
		if (data.getScoring() != null)
			readScoringAndCategory(data.getScoring(), matchBean);

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
					if (Boolean.TRUE.equals(soi.isActive())) {
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
		return matchBean;
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
