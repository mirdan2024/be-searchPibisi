package it.search.pibisi.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.search.pibisi.controller.pojo.PibisiPojo;

@Service
public class CustomersService {

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

	private RestTemplate restTemplate = new RestTemplate();

	// Metodo GET per ottenere i clienti di un account
	public ResponseEntity<String> getCustomersByAccountId(PibisiPojo requestJson) {
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

	// Metodo POST per creare un cliente in monitoraggio
	public ResponseEntity<String> createCustomer(PibisiPojo requestJson) {
		try {
			String url = postCustomersEndpoint.replace("{accountId}", requestJson.getAccountId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			String requestBody = "[{\"type\": \"" + requestJson.getType() + "\", \"content\": \""
					+ requestJson.getContent() + "\"},{\"type\": \"" + requestJson.getTypePerson()
					+ "\", \"content\": \"" + requestJson.getContentPerson() + "\"}]";

			Map<Object, Object> data = new HashMap<>();
			data.put("pois", requestBody);

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

	public ResponseEntity<String> findCustomer(PibisiPojo requestJson) {
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

	public ResponseEntity<String> getCustomersByCustomerId(PibisiPojo requestJson) {
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
	public ResponseEntity<String> activateCustomer(PibisiPojo requestJson) {
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

	public ResponseEntity<String> alertCustomer(PibisiPojo requestJson) {
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
	public ResponseEntity<String> changeCustomerRisk(PibisiPojo requestJson) {
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
	public ResponseEntity<String> deactivateCustomer(PibisiPojo requestJson) {
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
	public ResponseEntity<String> deleteCustomer(PibisiPojo requestJson) {
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

	public ResponseEntity<String> documentCustomer(PibisiPojo requestJson) {
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

	public ResponseEntity<String> matchesCustomer(PibisiPojo requestJson) {
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
	public ResponseEntity<String> rejectAllMatches(PibisiPojo requestJson) {
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
	public ResponseEntity<String> acceptMatch(PibisiPojo requestJson) {
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
	public ResponseEntity<String> rejectMatch(PibisiPojo requestJson) {
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
	public ResponseEntity<String> addCustomerPois(PibisiPojo requestJson) {
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

	public ResponseEntity<byte[]> getCustomerReport(PibisiPojo requestJson) {
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
