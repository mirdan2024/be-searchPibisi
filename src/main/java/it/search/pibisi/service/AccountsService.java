package it.search.pibisi.service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.AccountsSubjectsResponse;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.users.me.UsersMeResponse;
import it.search.pibisi.pojo.users.me.accounts.UsersMeAccountsResponse;

@Service
public class AccountsService extends BaseService {

	@Value("${mock.response.search}")
	private Boolean mockResponseSearch;

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

	// Metodo per ottenere le informazioni sugli account
	@Cacheable("accountId")
	public String getAccountId() {
		try {

			UsersMeAccountsResponse usersMeAccountsResponse = userMeAccounts();

			return usersMeAccountsResponse.getData().get(0).getAccount().getUuid();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per chiamare l'API e ottenere i dati dell'utente
	public UsersMeResponse userMe() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(userUrl, HttpMethod.GET, entity, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			return objectMapper.readValue(response.getBody(), UsersMeResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per ottenere le informazioni sugli account
	public UsersMeAccountsResponse userMeAccounts() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(accountsUrl, HttpMethod.GET, entity, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			return objectMapper.readValue(response.getBody(), UsersMeAccountsResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per fare una richiesta di ricerca
	public AccountsSubjectsFindResponse accountsSubjectsFind(PibisiPojo requestJson) {
		try {

			String accountId = getAccountId(requestJson);

			String url = findSubjectsUrl.replace("{accountId}", accountId);

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			StringBuilder sb = new StringBuilder();
			sb.append("[{");
			sb.append("\"type\": \"" + requestJson.getType() + "\"");
			sb.append(", ");
			sb.append("\"content\": \"" + requestJson.getContent() + "\"");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}]");

			Map<Object, Object> data = new HashMap<>();
			data.put("pois", sb.toString());

			// Codifica dei dati in formato application/x-www-form-urlencoded
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<Object, Object> entry : data.entrySet()) {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);
			if (mockResponseSearch) {
				String responseBodyMock = "";
				if ("a".equalsIgnoreCase(requestJson.getContent())
						|| "José Ignacio Encinas".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Josee_Ignacio_Encinas.json");
				}
				if ("b".equalsIgnoreCase(requestJson.getContent())
						|| "José Jerónimo Enrile de Cárdenas".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Jose_Jeronimo_Enrile_de_Cardenas.json");
				}
				if ("c".equalsIgnoreCase(requestJson.getContent())
						|| "Mohamed Jabir".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Mohamed_Jabir.json");
				}
				if ("d".equalsIgnoreCase(requestJson.getContent())
						|| "Rabah Naami Abou".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Rabah_Naami_Abou.json");
				}
				if ("e".equalsIgnoreCase(requestJson.getContent())
						|| "Battisti Cesare".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Battisti_Cesare.json");
				}
				if ("f".equalsIgnoreCase(requestJson.getContent())
						|| "Berlusconi Pier Silvio".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Berlusconi_Pier_Silvio.json");
				}
				if ("g".equalsIgnoreCase(requestJson.getContent())
						|| "Casamonica Guerrino".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Casamonica_Guerrino.json");
				}
				if ("h".equalsIgnoreCase(requestJson.getContent())
						|| "Giorgia Meloni".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Giorgia_Meloni.json");
				}
				if ("i".equalsIgnoreCase(requestJson.getContent())
						|| "Salvini Matteo".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Salvini_Matteo.json");
				}
				if ("s".equalsIgnoreCase(requestJson.getContent())
						|| "Berlusconi Silvio".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Berlusconi_Silvio.json");
				}
				if ("p".equalsIgnoreCase(requestJson.getContent())
						|| "Berlusconi Pier Silvio".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Berlusconi_Pier_Silvio.json");
				}
				return objectMapper.readValue(responseBodyMock, AccountsSubjectsFindResponse.class);
			}

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			return objectMapper.readValue(response.getBody(), AccountsSubjectsFindResponse.class);
		} catch (

		Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String readFile(String filePath) throws IOException {
		File file = ResourceUtils.getFile("classpath:" + filePath);
		byte[] fileData = Files.readAllBytes(Paths.get(file.getPath()));
		return new String(fileData);
	}

	// Metodo per ottenere i dettagli di un soggetto
	public AccountsSubjectsResponse accountsSubjects(PibisiPojo requestJson) {
		try {
			String url = subjectDetailsUrl.replace("{accountId}", requestJson.getAccountId()).replace("{subjectId}",
					requestJson.getSubjectId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			HttpEntity<String> entity = new HttpEntity<>(headers);
			if (mockResponseSearch) {
				String responseBodyMock = "";
				if ("a".equalsIgnoreCase(requestJson.getSubjectId()) ||  "e709f2d3-3f59-4724-992c-1030158276bf".equalsIgnoreCase(requestJson.getSubjectId())
						|| "José Ignacio Encinas".equalsIgnoreCase(requestJson.getSubjectId())) {
					responseBodyMock = readFile("Josee_Ignacio_Encinas - detail.json");
				}
				if ("c".equalsIgnoreCase(requestJson.getSubjectId()) || "b764362a-d0d2-4282-b82c-9e98cf3de9a0".equalsIgnoreCase(requestJson.getSubjectId())
						|| "Mohamed Jabir".equalsIgnoreCase(requestJson.getSubjectId())) {
					responseBodyMock = readFile("Mohamed_Jabir_detail.json");
				}
				if ("d".equalsIgnoreCase(requestJson.getSubjectId()) || "2900cbbe-0af4-4ca8-ac34-0c3321dc67fc".equalsIgnoreCase(requestJson.getSubjectId())
						|| "Rabah Naami Abou".equalsIgnoreCase(requestJson.getSubjectId())) {
					responseBodyMock = readFile("Rabah_Naami_Abou_detail.json");
				}
				if ("s".equalsIgnoreCase(requestJson.getSubjectId())
						|| "Berlusconi Silvio".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Berlusconi_Silvio_detail.json");
				}
				if ("p".equalsIgnoreCase(requestJson.getSubjectId())
						|| "Berlusconi Pier Silvio".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Berlusconi_Pier_Silvio_detail.json");
				}
				return objectMapper.readValue(responseBodyMock, AccountsSubjectsResponse.class);
			}

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			return objectMapper.readValue(response.getBody(), AccountsSubjectsResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per ottenere i dettagli di un soggetto
	public String accountsSubjectsJson(PibisiPojo requestJson) {
		try {
			String url = subjectDetailsUrl.replace("{accountId}", requestJson.getAccountId()).replace("{subjectId}",
					requestJson.getSubjectId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per fare una richiesta POST all'endpoint
	public AccountsSubjectsFindResponse accountsSubjectsFindBlocked(PibisiPojo requestJson) {
		try {
			String url = findBlockedSubjectsUrl.replace("{accountId}", requestJson.getAccountId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-AUTH-TOKEN", token);
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

			StringBuilder sb = new StringBuilder();
			sb.append("[{");
			sb.append("\"type\": \"" + requestJson.getType() + "\"");
			sb.append(", ");
			sb.append("\"content\": \"" + requestJson.getContent() + "\"");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}]");

			Map<Object, Object> data = new HashMap<>();
			data.put("pois", sb.toString());

			// Codifica dei dati in formato application/x-www-form-urlencoded
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<Object, Object> entry : data.entrySet()) {
				sj.add(URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			HttpEntity<String> entity = new HttpEntity<>(sj.toString(), headers);
			if (mockResponseSearch) {
				String responseBodyMock = "";
				if ("a".equalsIgnoreCase(requestJson.getContent())
						|| "José Ignacio Encinas".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("José Ignacio Encinas.json");
				}
				if ("b".equalsIgnoreCase(requestJson.getContent())
						|| "José Jerónimo Enrile de Cárdenas".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Josee_Jeronimo_Enrile_de_Cardenas.json");
				}
				if ("c".equalsIgnoreCase(requestJson.getContent())
						|| "Mohamed Jabir".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Mohamed_Jabir.json");
				}
				if ("d".equalsIgnoreCase(requestJson.getContent())
						|| "Rabah Naami Abou".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Rabah_Naami_Abou.json");
				}
				if ("e".equalsIgnoreCase(requestJson.getContent())
						|| "Battisti Cesare".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Battisti_Cesare.json");
				}
				if ("f".equalsIgnoreCase(requestJson.getContent())
						|| "Berlusconi Pier Silvio".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Berlusconi_Pier_Silvio.json");
				}
				if ("g".equalsIgnoreCase(requestJson.getContent())
						|| "Casamonica Guerrino".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Casamonica_Guerrino.json");
				}
				if ("h".equalsIgnoreCase(requestJson.getContent())
						|| "Giorgia Meloni".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Giorgia_Meloni.json");
				}
				if ("i".equalsIgnoreCase(requestJson.getContent())
						|| "Salvini Matteo".equalsIgnoreCase(requestJson.getContent())) {
					responseBodyMock = readFile("Salvini_Matteo.json");
				}
			
				return objectMapper.readValue(responseBodyMock, AccountsSubjectsFindResponse.class);
			}

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			return objectMapper.readValue(response.getBody(), AccountsSubjectsFindResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per ottenere un report di un soggetto
	public ResponseEntity<byte[]> accountsSubjectsReport(PibisiPojo requestJson) {
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
}
