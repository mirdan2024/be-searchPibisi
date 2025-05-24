package it.search.pibisi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.common.base.ListaCategorieGruppoPojo;
import it.common.pibisi.bean.MatchBean;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UtilsService {
	@Value("${api.base.lista.categorie}")
	private String urlApiBaseListaCategorie;

	@Value("${api.base.tracciamento.crediti}")
	private String urlApiBaseTracciamentoCrediti;

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
	public ListaCategorieGruppoPojo callGetListaCategorie(HttpServletRequest request) {
		// 1. Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("X-Forwarded-For", getClientIp(request));

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			if (!headerName.contains("Forwarded")) {
				headers.add(headerName, request.getHeader(headerName));
			}
		}

		// 2. Configura RestTemplate
		RestTemplate restTemplate = restTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		// 3. Chiamata GET senza body, ma con headers
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		String nomeCategoriaGruppo = "Pibisi";

		ResponseEntity<ListaCategorieGruppoPojo> response = restTemplate.exchange(
				urlApiBaseListaCategorie + nomeCategoriaGruppo, HttpMethod.GET, entity, ListaCategorieGruppoPojo.class);
		return response.getBody();
	}

	public void callTracciamentoCrediti(AccountsSearchPojo requestJson, HttpServletRequest request) throws Exception {

		try {
			// 1. Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(List.of(MediaType.APPLICATION_JSON));
			headers.set("Content-Type", "application/json;charset=UTF-8");
			headers.add("X-Forwarded-For", getClientIp(request));

			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				if (!headerName.contains("Forwarded")) {
					headers.add(headerName, request.getHeader(headerName));
				}
			}

			// 2. Body
			Map<String, String> requestBody = new HashMap<>();
			if (requestJson.getAccountId() != null)
				requestBody.put("accountId", requestJson.getAccountId());
			if (requestJson.getSubjectId() != null)
				requestBody.put("subjectId", requestJson.getSubjectId());
			if (requestJson.getNameFull() != null)
				requestBody.put("nameFull", requestJson.getNameFull());
			if (requestJson.getBirthDate() != null)
				requestBody.put("birthDate", requestJson.getBirthDate());
			if (requestJson.getBirthPlace() != null)
				requestBody.put("birthPlace", requestJson.getBirthPlace());
			if (requestJson.getGender() != null)
				requestBody.put("gender", requestJson.getGender());
			if (requestJson.getNationality() != null)
				requestBody.put("nationality", requestJson.getNationality());
			if (requestJson.getPerson() != null)
				requestBody.put("person", requestJson.getPerson());
			if (requestJson.getThreshold() != null)
				requestBody.put("threshold", requestJson.getThreshold());
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonBody = objectMapper.writeValueAsString(requestBody);

			// 3. Configura RestTemplate
			RestTemplate restTemplate = restTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			// 4. Chiamata POST con body
			String nomeCategoriaGruppo = "Pibisi";
			restTemplate.exchange(urlApiBaseTracciamentoCrediti + nomeCategoriaGruppo, HttpMethod.POST,
					new HttpEntity<>(jsonBody, headers), Void.class);
		} catch (JsonProcessingException e) {
			throw new Exception("Errore, JSON input errato", e);
		} catch (Exception ex) {
			throw new Exception("Errore, nel servizio di tracciamento dei crediti", ex);
		}
	}

	@Bean
	public RestTemplate restTemplate() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(factory);
	}

	public StringBuilder setFilter(AccountsSearchPojo requestJson) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (StringUtils.hasLength(requestJson.getNameFull())) {
			sb.append("{");
			sb.append("\"type\": \"" + requestJson.getNameFullType() + "\"");
			sb.append(", ");
			sb.append("\"content\": \"" + requestJson.getNameFull() + "\"");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}");
		}
		if (StringUtils.hasLength(requestJson.getBirthDate())) {
			sb.append(",{");
			sb.append("\"type\": \"" + requestJson.getBirthDateType() + "\"");
			sb.append(", ");
			sb.append("\"content\": \"" + requestJson.getBirthDate() + "\"");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}");
		}
		if (StringUtils.hasLength(requestJson.getBirthPlace())) {
			sb.append(",{");
			sb.append("\"type\": \"" + requestJson.getBirthPlaceType() + "\"");
			sb.append(", ");
			sb.append("\"content\": {");
			sb.append("\"country\": \"" + requestJson.getBirthPlace() + "\"");
			sb.append("}");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}");
		}
		if (StringUtils.hasLength(requestJson.getGender())) {
			sb.append(",{");
			sb.append("\"type\": \"" + requestJson.getGenderType() + "\"");
			sb.append(", ");
			sb.append("\"content\": \"" + requestJson.getGender() + "\"");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}");
		}
		if (StringUtils.hasLength(requestJson.getNationality())) {
			sb.append(",{");
			sb.append("\"type\": \"" + requestJson.getNationalityType() + "\"");
			sb.append(", ");
			sb.append("\"content\": \"" + requestJson.getNationality() + "\"");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}");
		}
		if (StringUtils.hasLength(requestJson.getPerson())) {
			sb.append(",{");
			sb.append("\"type\": \"" + requestJson.getPersonType() + "\"");
			sb.append(", ");
			sb.append("\"content\": \"" + requestJson.getPerson() + "\"");
			if (StringUtils.hasLength(requestJson.getThreshold())) {
				sb.append(", ");
				sb.append("\"threshold\": \"" + requestJson.getThreshold() + "\"");
			}
			sb.append("}");
		}
		sb.append("]");
		return sb;
	}

	public List<String> getListaCategorie(HttpServletRequest request) {
		ListaCategorieGruppoPojo lcgp = callGetListaCategorie(request);
		List<String> listCategorie = new ArrayList<>();
		lcgp.getCategorieGruppoPojo().forEach(e -> {
			e.getListaCategorie().forEach(c -> {
				listCategorie.add(c.getCategoria());
			});
		});
		return listCategorie;
	}

	public Boolean matchCategory(MatchBean matchBean, List<String> listCategorie) {
		if ((matchBean.isPep() || matchBean.isWasPep()) && !listCategorie.contains("PEP")) {
			return Boolean.FALSE;
		}
		if ((matchBean.isSanctioned() || matchBean.isWasSanctioned()) && !listCategorie.contains("Sanction")) {
			return Boolean.FALSE;
		}
		if (matchBean.isTerrorist() && !listCategorie.contains("Terrorist")) {
			return Boolean.FALSE;
		}
		if (matchBean.isHasMedia() && !listCategorie.contains("Adverse media")) {
			return Boolean.FALSE;
		}
		if (matchBean.isHasAdverseInfo() && !listCategorie.contains("Adverse info")) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}
}