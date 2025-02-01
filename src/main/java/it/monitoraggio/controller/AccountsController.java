package it.monitoraggio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggio.service.AccountsService;

@RestController
public class AccountsController {

	@Autowired
	private AccountsService accountsService;

	// Endpoint per ottenere i dati dell'utente
	@GetMapping("/user")
	public ResponseEntity<String> getUser() {
		return accountsService.getUserData();
	}

	// Endpoint per ottenere le informazioni sugli account dell'utente
	@GetMapping("/user/accounts")
	public ResponseEntity<String> getUserAccounts() {
		return accountsService.getUserAccounts();
	}

	// Endpoint per ottenere i dettagli di un soggetto
	@GetMapping("/accounts/{accountId}/subjects/{subjectId}")
	public ResponseEntity<String> getSubjectDetails(@PathVariable String accountId, @PathVariable String subjectId) {
		return accountsService.getSubjectDetails(accountId, subjectId);
	}

	// Endpoint per ottenere un report di un soggetto
	@GetMapping("/accounts/{accountId}/subjects/{subjectId}/report")
	public ResponseEntity<String> getSubjectReport(@PathVariable String accountId, @PathVariable String subjectId) {
		return accountsService.getSubjectReport(accountId, subjectId);
	}

	// Endpoint per inviare una richiesta di ricerca
	@PostMapping("/accounts/{accountId}/subjects/find")
	public ResponseEntity<String> findSubjects(@PathVariable String accountId, @PathVariable String nameFull) {
		// Corpo della richiesta con il valore per "pois"
		String requestBody = "{\"pois\": [{\"type\": \"name.full\", \"content\": \"" + nameFull + "\"}]}";
		return accountsService.findSubjectsForAccount(accountId, requestBody);
	}

	// Endpoint per inviare una richiesta di ricerca
	@PostMapping("/accounts/{accountId}/subjects/find-blocked")
	public ResponseEntity<String> findBlockedSubjects(@PathVariable String accountId, @PathVariable String nameFull) {
		// Corpo della richiesta con il valore per "pois"
		String requestBody = "{\"pois\": [{\"type\": \"name.full\", \"content\": \"" + nameFull + "\"}]}";
		return accountsService.findBlockedSubjectsForAccount(accountId, requestBody);
	}
}
