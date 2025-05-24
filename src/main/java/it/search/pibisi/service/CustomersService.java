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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.util.JWTUtil;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.pojo.customers.create.CustomersCreateResponse;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomersService {

	private static final Logger logger = LoggerFactory.getLogger(CustomersService.class);

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

	@Value("${api.customers.get}")
	private String getCustomersEndpoint;

	@Value("${api.customers.post}")
	private String postCustomersEndpoint;

	@Value("${api.customers.find}")
	private String findCustomerEndpoint;

	@Value("${api.customers.customers}")
	private String customersEndpoint;

	@Value("${api.customers.activate}")
	private String activateCustomerEndpoint;

	@Value("${api.customers.alert}")
	private String alertCustomerEndpoint;

	@Value("${api.customers.change-risk}")
	private String changeRiskEndpoint;

	@Value("${api.customers.deactivate}")
	private String deactivateCustomerEndpoint;

	@Value("${api.customers.delete}")
	private String deleteCustomerEndpoint;

	@Value("${api.customers.documents}")
	private String documentEndpoint;

	@Value("${api.customers.matches}")
	private String matchesEndpoint;

	@Value("${api.customers.matches.reject-all}")
	private String rejectAllMatchesEndpoint;

	@Value("${api.customers.matches.accept}")
	private String acceptMatchEndpoint;

	@Value("${api.customers.matches.reject}")
	private String rejectMatchEndpoint;

	@Value("${api.customers.pois}")
	private String addPoisEndpoint;

	@Value("${api.customers.report}")
	private String getCustomerReportEndpoint;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private JWTUtil jwtUtil;

	private RestTemplate restTemplate = new RestTemplate();

	private final ResourceBundle bundle = ResourceBundle.getBundle("bundle.messages-errors");

	// Metodo POST per creare un cliente in monitoraggio
	public CustomersCreateResponse createCustomer(AccountsSearchPojo requestJson, HttpServletRequest request) {
		if (!StringUtils.hasLength(requestJson.getNameFull()) || !StringUtils.hasLength(requestJson.getPerson())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					bundle.getString("error.both.namefull.person.required"));
		}

		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		String body = callPostCustomersEndpoint(requestJson, map);
		if (body != null && !body.trim().isEmpty()) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return objectMapper.readValue(body, CustomersCreateResponse.class);
			} catch (JsonMappingException e) {
				logger.error(bundle.getString("json.mapping.error"), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("json.mapping.error"));
			} catch (JsonProcessingException e) {
				logger.error(bundle.getString("json.processing.error"), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("json.processing.error"));
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, bundle.getString("error.record.not.found"));
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
				logger.error(bundle.getString("error.encoding"), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("error.encoding"));
			}
		}

		HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		return response.getBody();
	}

	// Metodo GET per ottenere i clienti di un account
	public ResponseEntity<String> getCustomersByAccountId(AccountsSearchPojo requestJson) {
		try {
			String url = getCustomersEndpoint.replace("{accountId}", requestJson.getAccountId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<String> findCustomer(AccountsSearchPojo requestJson) {
		try {
			String url = findCustomerEndpoint.replace("{accountId}", requestJson.getAccountId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			Map<Object, Object> data = new HashMap<>();
			data.put("search", requestJson.getSearch());

			// Codifica dei dati in formato application/x-www-form-urlencoded
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<Object, Object> entry : data.entrySet()) {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}

			HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);

			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<String> getCustomersByCustomerId(AccountsSearchPojo requestJson) {
		try {
			String url = customersEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
					requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per attivare un cliente
	public ResponseEntity<String> activateCustomer(AccountsSearchPojo requestJson) {
		try {
			String url = activateCustomerEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<String> alertCustomer(AccountsSearchPojo requestJson) {
		try {
			String url = alertCustomerEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per cambiare il rischio di un cliente
	public ResponseEntity<String> changeCustomerRisk(AccountsSearchPojo requestJson) {
		try {
			String url = changeRiskEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
					requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			Map<Object, Object> data = new HashMap<>();
			data.put("risk", requestJson.getRisk());

			// Codifica dei dati in formato application/x-www-form-urlencoded
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<Object, Object> entry : data.entrySet()) {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}

			HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);

			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per disattivare un cliente
	public ResponseEntity<String> deactivateCustomer(AccountsSearchPojo requestJson) {
		try {
			String url = deactivateCustomerEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per eliminare un cliente
	public ResponseEntity<String> deleteCustomer(AccountsSearchPojo requestJson) {
		try {
			String url = deleteCustomerEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<String> documentCustomer(AccountsSearchPojo requestJson) {
		try {
			String url = documentEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
					requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<String> matchesCustomer(AccountsSearchPojo requestJson) {
		try {
			String url = matchesEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
					requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per rifiutare tutti i match di un cliente
	public ResponseEntity<String> rejectAllMatches(AccountsSearchPojo requestJson) {
		try {
			String url = rejectAllMatchesEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per accettare un match del cliente
	public ResponseEntity<String> acceptMatch(AccountsSearchPojo requestJson) {
		try {
			String url = acceptMatchEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId())
					.replace("{matchId}", requestJson.getMatchId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			Map<Object, Object> data = new HashMap<>();
			data.put("comment", requestJson.getComment());

			// Codifica dei dati in formato application/x-www-form-urlencoded
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<Object, Object> entry : data.entrySet()) {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}

			HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);

			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per rifiutare un match del cliente
	public ResponseEntity<String> rejectMatch(AccountsSearchPojo requestJson) {
		try {
			String url = rejectMatchEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId())
					.replace("{matchId}", requestJson.getMatchId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			Map<Object, Object> data = new HashMap<>();
			data.put("comment", requestJson.getComment());

			// Codifica dei dati in formato application/x-www-form-urlencoded
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<Object, Object> entry : data.entrySet()) {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}

			HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);

			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo POST per aggiungere i points of information (pois)
	public ResponseEntity<String> addCustomerPois(AccountsSearchPojo requestJson) {
		try {
			String url = addPoisEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
					requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			Map<Object, Object> data = new HashMap<>();
			data.put("data", requestJson.getData());

			// Codifica dei dati in formato application/x-www-form-urlencoded
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<Object, Object> entry : data.entrySet()) {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}

			HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);

			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<byte[]> getCustomerReport(AccountsSearchPojo requestJson) {
		try {
			String url = getCustomerReportEndpoint.replace("{accountId}", requestJson.getAccountId())
					.replace("{customerId}", requestJson.getCustomerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			return restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
