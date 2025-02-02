package it.search.pibisi.controller.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.search.pibisi.controller.CustomersController;
import it.search.pibisi.service.CustomersService;

@SpringBootTest
public class CustomersControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CustomersService customersService;

	@Value("${api.token}")
	private String token;

	@Value("${api.customers.get}")
	private String getCustomersEndpoint;

	private final String accountId = "12345";
	private final String customerId = "67890";
	private final String matchId = "112233";
	private final String pois = "{\"name\":\"John Doe\", \"email\":\"johndoe@example.com\"}";

	private String expectedUrl;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new CustomersController()).build();
		expectedUrl = getCustomersEndpoint;
	}

	@Test
	public void testGetCustomersByAccountId() throws Exception {
		// Simuliamo la risposta del servizio
		String mockResponse = "{\"customers\": [{\"id\": \"67890\", \"name\": \"John Doe\"}]}";
//		when(customersService.getCustomersByAccountId(accountId)).thenReturn(mockResponse);

		// Eseguiamo il test dell'endpoint GET
		mockMvc.perform(
				MockMvcRequestBuilders.get("/accounts/{accountId}/customers", accountId).header("X-AUTH-TOKEN", token))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().json(mockResponse));

		// Verifica che il servizio sia stato chiamato
//		verify(customersService, times(1)).getCustomersByAccountId(accountId);
	}

	@Test
	public void testCreateCustomer() throws Exception {
		String url = "/customers";
		String requestBody = "pois=" + pois;

		// Simuliamo la risposta del servizio
		String mockResponse = "{\"status\": \"success\", \"message\": \"Customer created\"}";
//		when(customersService.createCustomer(accountId, pois))
//				.thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.CREATED));

		// Eseguiamo il test dell'endpoint POST
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/{accountId}/customers", accountId)
				.header("X-AUTH-TOKEN", token).contentType(MediaType.APPLICATION_FORM_URLENCODED).content(requestBody))
				.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.content().json(mockResponse));

		// Verifica che il servizio sia stato chiamato
//		verify(customersService, times(1)).createCustomer(accountId, pois);
	}

	@Test
	public void testActivateCustomer() throws Exception {
		String url = "/customers/" + customerId + "/activate";

		// Simuliamo la risposta del servizio
		String mockResponse = "{\"status\": \"success\", \"message\": \"Customer activated\"}";
//		when(customersService.activateCustomer(accountId, customerId))
//				.thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

		// Eseguiamo il test dell'endpoint POST
		mockMvc.perform(MockMvcRequestBuilders
				.post("/accounts/{accountId}/customers/{customerId}/activate", accountId, customerId)
				.header("X-AUTH-TOKEN", token)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(mockResponse));

		// Verifica che il servizio sia stato chiamato
//		verify(customersService, times(1)).activateCustomer(accountId, customerId);
	}

	@Test
	public void testRejectAllMatches() throws Exception {
		String url = "/customers/" + customerId + "/matches/reject-all";

		// Simuliamo la risposta del servizio
		String mockResponse = "{\"status\": \"success\", \"message\": \"All matches rejected\"}";
//		when(customersService.rejectAllMatches(accountId, customerId))
//				.thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

		// Eseguiamo il test dell'endpoint POST
		mockMvc.perform(MockMvcRequestBuilders
				.post("/accounts/{accountId}/customers/{customerId}/matches/reject-all", accountId, customerId)
				.header("X-AUTH-TOKEN", token)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(mockResponse));

		// Verifica che il servizio sia stato chiamato
//		verify(customersService, times(1)).rejectAllMatches(accountId, customerId);
	}
}
