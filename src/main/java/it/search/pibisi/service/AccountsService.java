package it.search.pibisi.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.search.pibisi.controller.pojo.PibisiPojo;

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

	// Metodo per ottenere un report di un soggetto
	public ResponseEntity<byte[]> getSubjectReport(PibisiPojo requestJson) {
		try {
			String url = subjectReportUrl.replace("{accountId}", requestJson.getAccountId()).replace("{subjectId}",
					requestJson.getSubjectId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			return restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per ottenere i dettagli di un soggetto
	public ResponseEntity<String> getSubjectDetails(PibisiPojo requestJson) {
		try {
			String url = subjectDetailsUrl.replace("{accountId}", requestJson.getAccountId()).replace("{subjectId}",
					requestJson.getSubjectId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per fare una richiesta di ricerca
	public ResponseEntity<String> findSubjectsForAccount(PibisiPojo requestJson) {
		try {
			String url = findSubjectsUrl.replace("{accountId}", requestJson.getAccountId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			String requestBody = "[{\"type\": \"" + requestJson.getType() + "\", \"content\": \""
					+ requestJson.getContent() + "\"}]";

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

	// Metodo per fare una richiesta POST all'endpoint
	// /accounts/{accounts}/subjects/find-blocked
	public ResponseEntity<String> findBlockedSubjectsForAccount(PibisiPojo requestJson) {
		try {
			String url = findBlockedSubjectsUrl.replace("{accountId}", requestJson.getAccountId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			String requestBody = "[{\"type\": \"" + requestJson.getType() + "\", \"content\": \""
					+ requestJson.getContent() + "\"}]";

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
}
