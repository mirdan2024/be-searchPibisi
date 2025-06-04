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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import it.common.base.util.JWTUtil;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomersService {

	private static final Logger logger = LoggerFactory.getLogger(CustomersService.class);

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

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
	private JWTUtil jwtUtil;

	private RestTemplate restTemplate = new RestTemplate();

	private final ResourceBundle bundle = ResourceBundle.getBundle("bundle.messages-errors");

	public ResponseEntity<String> find(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = findCustomerEndpoint.replace("{accountId}", requestJson.getAccountId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		Map<Object, Object> data = new HashMap<>();
		data.put("search", requestJson.getSearch());

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

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

	}

	// Metodo POST per attivare un cliente
	public ResponseEntity<String> activate(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = activateCustomerEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
				requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per disattivare un cliente
	public ResponseEntity<String> deactivate(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = deactivateCustomerEndpoint.replace("{accountId}", requestJson.getAccountId())
				.replace("{customerId}", requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per eliminare un cliente
	public ResponseEntity<String> delete(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = deleteCustomerEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
				requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	public ResponseEntity<String> alert(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = alertCustomerEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
				requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	}

	// Metodo POST per accettare un match del cliente
	public ResponseEntity<String> accept(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = acceptMatchEndpoint.replace("{accountId}", requestJson.getAccountId())
				.replace("{customerId}", requestJson.getCustomerId()).replace("{matchId}", requestJson.getMatchId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		Map<Object, Object> data = new HashMap<>();
		data.put("comment", requestJson.getComment());

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

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per rifiutare un match del cliente
	public ResponseEntity<String> reject(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = rejectMatchEndpoint.replace("{accountId}", requestJson.getAccountId())
				.replace("{customerId}", requestJson.getCustomerId()).replace("{matchId}", requestJson.getMatchId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		Map<Object, Object> data = new HashMap<>();
		data.put("comment", requestJson.getComment());

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

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per cambiare il rischio di un cliente
	public ResponseEntity<String> changeRisk(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

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
			try {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error(bundle.getString("error.encoding"), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("error.encoding"));
			}
		}

		HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	public ResponseEntity<String> documents(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = documentEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
				requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	}

	// Metodo POST per rifiutare tutti i match di un cliente
	public ResponseEntity<String> rejectAll(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = rejectAllMatchesEndpoint.replace("{accountId}", requestJson.getAccountId()).replace("{customerId}",
				requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per aggiungere i points of information (pois)
	public ResponseEntity<String> addPois(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

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
			try {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error(bundle.getString("error.encoding"), e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("error.encoding"));
			}
		}

		HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	public ResponseEntity<byte[]> report(AccountsSearchPojo requestJson, HttpServletRequest request) {
		HashMap<String, String> map = jwtUtil.getInfoFromJwt(request);
		requestJson.setAccountId(map.get("accountId"));

		String url = getCustomerReportEndpoint.replace("{accountId}", requestJson.getAccountId())
				.replace("{customerId}", requestJson.getCustomerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		return restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
	}
}
