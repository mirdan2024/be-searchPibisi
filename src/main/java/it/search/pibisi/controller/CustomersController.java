package it.search.pibisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.service.CustomersService;

@RestController
public class CustomersController {

	@Autowired
	private CustomersService customersService;

	// Metodo GET per ottenere i clienti di un account
	@GetMapping("/accounts/customers")
	public ResponseEntity<String> getCustomersByAccountId(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.getCustomersByAccountId(requestJson);
	}

	// Nuovo metodo POST per inviare i dati dei clienti
	@PostMapping("/accounts/customers")
	public ResponseEntity<String> createCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.createCustomer(requestJson);
	}

	// Attivazione di un cliente
	@PostMapping("accounts/customers/find")
	public ResponseEntity<String> findCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.findCustomer(requestJson);
	}

	// Metodo GET per ottenere i clienti di un account
	@GetMapping("accounts/customers/customers")
	public ResponseEntity<String> getCustomersByCustomerId(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.getCustomersByCustomerId(requestJson);
	}

	// Attivazione di un cliente
	@PostMapping("/accounts/customers/activate")
	public ResponseEntity<String> activateCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.activateCustomer(requestJson);
	}

	// Recupero alert
	@GetMapping("/accounts/customers/alerts")
	public ResponseEntity<String> alertCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.alertCustomer(requestJson);
	}

	// Cambiare il rischio di un cliente
	@PostMapping("/accounts/customers/change-risk")
	public ResponseEntity<String> changeCustomerRisk(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.changeCustomerRisk(requestJson);
	}

	// Disattivazione di un cliente
	@PostMapping("/accounts/customers/deactivate")
	public ResponseEntity<String> deactivateCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.deactivateCustomer(requestJson);
	}

	// Eliminazione di un cliente
	@PostMapping("/accounts/customers/delete")
	public ResponseEntity<String> deleteCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.deleteCustomer(requestJson);
	}

	@GetMapping("/accounts/customers/documents")
	public ResponseEntity<String> documentCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.documentCustomer(requestJson);
	}

	@GetMapping("/accounts/customers/matches")
	public ResponseEntity<String> matchesCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.matchesCustomer(requestJson);
	}

	// Rifiutare tutti i match di un cliente
	@PostMapping("/accounts/customers/matches/reject-all")
	public ResponseEntity<String> rejectAllMatches(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.rejectAllMatches(requestJson);
	}

	// Accettare un match del cliente
	@PostMapping("/accounts/customers/matches/accept")
	public ResponseEntity<String> acceptMatch(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.acceptMatch(requestJson);
	}

	// Rifiutare un match del cliente
	@PostMapping("/accounts/customers/matches/reject")
	public ResponseEntity<String> rejectMatch(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.rejectMatch(requestJson);
	}

	// Aggiungere points of information (pois) per un cliente
	@PostMapping("/accounts/customers/pois")
	public ResponseEntity<String> addCustomerPois(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.addCustomerPois(requestJson);
	}

	// Endpoint per ottenere un report di un soggetto
	@GetMapping("/accounts/customers/report")
	public ResponseEntity<byte[]> getCustomerReport(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.getCustomerReport(requestJson);
	}

}
