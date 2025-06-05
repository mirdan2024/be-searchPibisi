package it.search.pibisi.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.message.MessageService;
import it.common.base.util.JWTUtil;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import it.common.pibisi.pojo.customers.create.CustomersCreateResponse;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomersCreateService {

	private static final Logger logger = LoggerFactory.getLogger(CustomersCreateService.class);

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

	@Value("${api.customers.post}")
	private String postCustomersEndpoint;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	public MessageService messageService;

	// Metodo POST per creare un cliente in monitoraggio
	public CustomersCreateResponse create(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		if (!StringUtils.hasLength(requestJson.getNameFull()) || !StringUtils.hasLength(requestJson.getPerson())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					messageService.get("error.both.namefull.person.required",map.get("lingua")).toString());
		}

		
		String body = callPostCustomersEndpoint(requestJson, map);
		if (body != null && !body.trim().isEmpty()) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return objectMapper.readValue(body, CustomersCreateResponse.class);
			} catch (JsonMappingException e) {
				logger.error(messageService.get("json.mapping.error",map.get("lingua")), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("json.mapping.error",map.get("lingua")));
			} catch (JsonProcessingException e) {
				logger.error(messageService.get("json.processing.error",map.get("lingua")), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("json.processing.error",map.get("lingua")));
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("error.monitoring",map.get("lingua")));
		}
	}

	private String callPostCustomersEndpoint(AccountsSearchPojo requestJson, HashMap<String, String> map) {
		String url = postCustomersEndpoint.replace("{accountId}", map.get("accountId"));

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		StringBuilder sb = utilsService.setFilter(requestJson);

		Map<Object, Object> data = new HashMap<>();
		data.put("pois", sb.toString());

		// Codifica dei dati in formato application/x-www-form-urlencoded
		StringJoiner sj = new StringJoiner("&");
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			try {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error(messageService.get("error.encoding",map.get("lingua")), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("error.encoding",map.get("lingua")));
			}
		}

		HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		return response.getBody();
	}

}
