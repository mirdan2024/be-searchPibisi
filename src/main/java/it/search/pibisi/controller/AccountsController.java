package it.search.pibisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.search.pibisi.controller.pojo.PibisiPojo;
import it.search.pibisi.pojo.accounts.subjects.find.AccountsSubjectsFindResponse;
import it.search.pibisi.pojo.users.me.UsersMeResponse;
import it.search.pibisi.pojo.users.me.accounts.UsersMeAccountsResponse;
import it.search.pibisi.service.AccountsService;

@RestController
public class AccountsController {

	@Autowired
	private AccountsService accountsService;

	// Endpoint per ottenere i dati dell'utente
	@GetMapping("/user")
	public UsersMeResponse userMe() {
		return accountsService.userMe();
	}

	// Endpoint per ottenere le informazioni sugli account dell'utente
	@GetMapping("/user/accounts")
	public UsersMeAccountsResponse userMeAccounts() {
		return accountsService.userMeAccounts();
	}

	// Endpoint per inviare una richiesta di ricerca
	@PostMapping("/accounts/subjects/find")
	public AccountsSubjectsFindResponse findSubjects(@RequestBody PibisiPojo requestJson) {
		// Corpo della richiesta con il valore per "pois"
		return accountsService.accountsSubjectsFind(requestJson);
	}
	
	
	
	
	
	
	
	
	

	// Endpoint per ottenere un report di un soggetto
	@GetMapping("/accounts/subjects/report")
	public ResponseEntity<byte[]> getSubjectReport(@RequestBody PibisiPojo requestJson) {
		return accountsService.getSubjectReport(requestJson);
	}

	// Endpoint per ottenere i dettagli di un soggetto
	@GetMapping("/accounts/subjects")
	public ResponseEntity<String> getSubjectDetails(@RequestBody PibisiPojo requestJson) {
		return accountsService.getSubjectDetails(requestJson);
	}

	// Endpoint per inviare una richiesta di ricerca
	@PostMapping("/accounts/subjects/find-blocked")
	public ResponseEntity<String> findBlockedSubjects(@RequestBody PibisiPojo requestJson) {
		// Corpo della richiesta con il valore per "pois"
		return accountsService.findBlockedSubjectsForAccount(requestJson);
	}
}
