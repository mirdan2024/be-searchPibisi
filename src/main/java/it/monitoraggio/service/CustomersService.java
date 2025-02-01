package it.monitoraggio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomersService {

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

	@Value("${api.customers.get}")
	private String getCustomersEndpoint;

	@Value("${api.customers.post}")
	private String postCustomersEndpoint;

	@Value("${api.customers.activate}")
	private String activateCustomerEndpoint;

	@Value("${api.customers.change-risk}")
	private String changeRiskEndpoint;

	@Value("${api.customers.deactivate}")
	private String deactivateCustomerEndpoint;

	@Value("${api.customers.delete}")
	private String deleteCustomerEndpoint;

	@Value("${api.customers.matches.reject-all}")
	private String rejectAllMatchesEndpoint;

	@Value("${api.customers.matches.accept}")
	private String acceptMatchEndpoint;

	@Value("${api.customers.matches.reject}")
	private String rejectMatchEndpoint;

	@Value("${api.customers.pois}")
	private String addPoisEndpoint;

	private RestTemplate restTemplate = new RestTemplate();

	// Metodo GET per ottenere i clienti di un account
	public String getCustomersByAccountId(String accountId) {
		String url = getCustomersEndpoint.replace("{accountId}", accountId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}

	// Metodo POST per creare un cliente
	public ResponseEntity<String> createCustomer(String accountId, String pois) {
		String url = postCustomersEndpoint.replace("{accountId}", accountId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "pois=" + pois;

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per attivare un cliente
	public ResponseEntity<String> activateCustomer(String accountId, String customerId) {
		String url = activateCustomerEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per cambiare il rischio di un cliente
	public ResponseEntity<String> changeCustomerRisk(String accountId, String customerId, String risk) {
		String url = changeRiskEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "risk=" + risk;

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per disattivare un cliente
	public ResponseEntity<String> deactivateCustomer(String accountId, String customerId) {
		String url = deactivateCustomerEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per eliminare un cliente
	public ResponseEntity<String> deleteCustomer(String accountId, String customerId) {
		String url = deleteCustomerEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per rifiutare tutti i match di un cliente
	public ResponseEntity<String> rejectAllMatches(String accountId, String customerId) {
		String url = rejectAllMatchesEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per accettare un match del cliente
	public ResponseEntity<String> acceptMatch(String accountId, String customerId, String matchId, String comment) {
		String url = acceptMatchEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId)
				.replace("{matchId}", matchId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "comment=" + comment;

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per rifiutare un match del cliente
	public ResponseEntity<String> rejectMatch(String accountId, String customerId, String matchId, String comment) {
		String url = rejectMatchEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId)
				.replace("{matchId}", matchId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "comment=" + comment;

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	// Metodo POST per aggiungere i points of information (pois)
	public ResponseEntity<String> addCustomerPois(String accountId, String customerId, String data) {
		String url = addPoisEndpoint.replace("{accountId}", accountId).replace("{customerId}", customerId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", token);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "data=" + data;

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}
}
