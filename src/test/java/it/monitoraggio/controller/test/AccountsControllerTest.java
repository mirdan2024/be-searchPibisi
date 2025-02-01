package it.monitoraggio.controller.test;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import it.monitoraggio.service.AccountsService;

@SpringBootTest
public class AccountsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountsService accountsService;

	@BeforeEach
	public void setUp() {
		// Simuliamo la risposta del servizio per getUserData
		String userData = "{\"name\":\"John Doe\",\"email\":\"johndoe@example.com\"}";
		when(accountsService.getUserData()).thenReturn(new ResponseEntity<>(userData, HttpStatus.OK));

		// Mock per l'endpoint /user/accounts
		String userAccounts = "[{\"account_id\": \"1\", \"account_name\": \"Main Account\"}, {\"account_id\": \"2\", \"account_name\": \"Secondary Account\"}]";
		when(accountsService.getUserAccounts()).thenReturn(new ResponseEntity<>(userAccounts, HttpStatus.OK));

		// Mock per l'endpoint /accounts/{accountId}/subjects/find-blocked
		String blockedSubjects = "{\"blocked_subjects\": [{\"subject_id\": \"1\", \"subject_name\": \"Blocked Subject A\"}, {\"subject_id\": \"2\", \"subject_name\": \"Blocked Subject B\"}]}";
		when(accountsService.findBlockedSubjectsForAccount(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(new ResponseEntity<>(blockedSubjects, HttpStatus.OK));
	}

	@Test
	public void testFindBlockedSubjects() throws Exception {
		// Simuliamo la risposta del servizio
		String mockResponse = "{\"blocked_subjects\": [{\"subject_id\": \"1\", \"subject_name\": \"Blocked Subject A\"}, {\"subject_id\": \"2\", \"subject_name\": \"Blocked Subject B\"}]}";
		// Test per l'endpoint POST "/accounts/{accountId}/subjects/find-blocked"
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/subjects/find-blocked"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(mockResponse));
	}

	@Test
	public void testFindSubjects() throws Exception {
		// Simuliamo la risposta del servizio
		String mockResponse = "{\"subjects\": [{\"subject_id\": \"1\", \"subject_name\": \"Subject A\"}]}";
		// Test per l'endpoint POST "/accounts/{accountId}/subjects/find"
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/subjects/find"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(mockResponse));
	}
}
