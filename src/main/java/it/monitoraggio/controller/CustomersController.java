package it.monitoraggio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.monitoraggio.service.CustomersService;

@RestController
public class CustomersController {

	@Autowired
	private CustomersService customersService;

	// Metodo GET per ottenere i clienti di un account
	@GetMapping("/accounts/{accountId}/customers")
	public String getCustomersByAccountId(@PathVariable("accountId") String accountId) {
		return customersService.getCustomersByAccountId(accountId);
	}

	// Nuovo metodo POST per inviare i dati dei clienti
	@PostMapping("/accounts/{accountId}/customers")
	public ResponseEntity<String> createCustomer(@PathVariable("accountId") String accountId,
			@RequestParam("pois") String pois) {
		return customersService.createCustomer(accountId, pois);
	}

	// Nuovi metodi POST per gli endpoint aggiuntivi

	// Attivazione di un cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/activate")
	public ResponseEntity<String> activateCustomer(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId) {
		return customersService.activateCustomer(accountId, customerId);
	}

	// Cambiare il rischio di un cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/change-risk")
	public ResponseEntity<String> changeCustomerRisk(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId, @RequestParam("risk") String risk) {
		return customersService.changeCustomerRisk(accountId, customerId, risk);
	}

	// Disattivazione di un cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/deactivate")
	public ResponseEntity<String> deactivateCustomer(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId) {
		return customersService.deactivateCustomer(accountId, customerId);
	}

	// Eliminazione di un cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/delete")
	public ResponseEntity<String> deleteCustomer(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId) {
		return customersService.deleteCustomer(accountId, customerId);
	}

	// Rifiutare tutti i match di un cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/matches/reject-all")
	public ResponseEntity<String> rejectAllMatches(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId) {
		return customersService.rejectAllMatches(accountId, customerId);
	}

	// Accettare un match del cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/matches/{matchId}/accept")
	public ResponseEntity<String> acceptMatch(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId, @PathVariable("matchId") String matchId,
			@RequestParam("comment") String comment) {
		return customersService.acceptMatch(accountId, customerId, matchId, comment);
	}

	// Rifiutare un match del cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/matches/{matchId}/reject")
	public ResponseEntity<String> rejectMatch(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId, @PathVariable("matchId") String matchId,
			@RequestParam("comment") String comment) {
		return customersService.rejectMatch(accountId, customerId, matchId, comment);
	}

	// Aggiungere points of information (pois) per un cliente
	@PostMapping("/accounts/{accountId}/customers/{customerId}/pois")
	public ResponseEntity<String> addCustomerPois(@PathVariable("accountId") String accountId,
			@PathVariable("customerId") String customerId, @RequestParam("data") String data) {
		return customersService.addCustomerPois(accountId, customerId, data);
	}
}
