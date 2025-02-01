package it.monitoraggio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountsService {

	// Token preso dal file application.properties
	@Value("${api.token}")
	private String token;

	@Value("${api.accounts.user-url}")
	private String userUrl;

	@Value("${api.accounts.accounts-url}")
	private String accountsUrl;

	@Value("${api.accounts.subject-details-url}")
	private String subjectDetailsUrl;

	@Value("${api.accounts.subject-report-url}")
	private String subjectReportUrl;

	@Value("${api.accounts.find-subjects-url}")
	private String findSubjectsUrl;

	@Value("${api.accounts.find-blocked-subjects-url}")
	private String findBlockedSubjectsUrl;

	private RestTemplate restTemplate = new RestTemplate();

	// Metodo per chiamare l'API e ottenere i dati dell'utente
	public ResponseEntity<String> getUserData() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			return restTemplate.exchange(userUrl, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per ottenere le informazioni sugli account
	public ResponseEntity<String> getUserAccounts() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			return restTemplate.exchange(accountsUrl, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per ottenere i dettagli di un soggetto
	public ResponseEntity<String> getSubjectDetails(String accountId, String subjectId) {
		try {
			String url = subjectDetailsUrl.replace("{accountId}", accountId).replace("{subjectId}", subjectId);

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per ottenere un report di un soggetto
	public ResponseEntity<String> getSubjectReport(String accountId, String subjectId) {
		try {
			String url = subjectReportUrl.replace("{accountId}", accountId).replace("{subjectId}", subjectId);

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per fare una richiesta POST all'endpoint
	// /accounts/{accounts}/subjects/find
	public ResponseEntity<String> findSubjectsForAccount(String accountId, String requestBody) {
		try {
			String url = findSubjectsUrl.replace("{accountId}", accountId);

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per fare una richiesta POST all'endpoint
	// /accounts/{accounts}/subjects/find-blocked
	public ResponseEntity<String> findBlockedSubjectsForAccount(String accountId, String requestBody) {
		try {
			String url = findBlockedSubjectsUrl.replace("{accountId}", accountId);

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
