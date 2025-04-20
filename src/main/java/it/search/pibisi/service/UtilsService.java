package it.search.pibisi.service;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.ListaCategorieGruppoPojo;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UtilsService {
	@Value("${api.base.lista.categorie}")
	private String urlApiBaseListaCategorie;

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static void copyNonNullProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}

	public String getClientIp(HttpServletRequest request) {

		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("X-Real-IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		// In caso di più IP in X-Forwarded-For, prendere il primo
		if (ipAddress != null && ipAddress.contains(",")) {
			ipAddress = ipAddress.split(",")[0].trim();
		}
		return ipAddress;
	}

	@Cacheable("callGetListaCategorie")
	public ListaCategorieGruppoPojo callGetListaCategorie(AccountsSearchPojo requestJson,
	                                                      HttpServletRequest request,
	                                                      String idIntermediario) throws JsonProcessingException {
	    RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Forwarded-For", getClientIp(request));

	    Enumeration<String> headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String headerName = headerNames.nextElement();
	        if (!headerName.contains("Forwarded")) {
	            headers.add(headerName, request.getHeader(headerName));
	        }
	    }

	    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
	    ResponseEntity<String> response = restTemplate.exchange(
	            urlApiBaseListaCategorie + idIntermediario,
	            HttpMethod.GET,
	            requestEntity,
	            String.class
	    );

	    String responseBody = response.getBody();
	    System.out.println("RESPONSE BODY:\n" + responseBody);

	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    // 👉 Filtro elementi nulli se serve, oppure gestisco errori jackson
	    try {
	        return objectMapper.readValue(responseBody, ListaCategorieGruppoPojo.class);
	    } catch (IllegalArgumentException e) {
	        System.err.println("Errore durante la deserializzazione: campo 'content' nullo o mancante.");
	        throw new JsonProcessingException("Errore parsing JSON: campo obbligatorio assente o nullo", e) {};
	    } catch (Exception e) {
	        System.err.println("Errore imprevisto durante il parsing JSON: " + e.getMessage());
	        throw new JsonProcessingException("Errore parsing JSON", e) {};
	    }
	}

}