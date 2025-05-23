package it.search.pibisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.service.CustomersService;

@RestController
@RequestMapping({ "/customers", "/api/customers" })
public class CustomersController {

	@Autowired
	private CustomersService customersService;
	
	// Registers a new person (customer) to follow up. You can send multiple pieces of information (aka. pois), but at least the types 'person' (allowed values: 'P' for natural person, and 'E' for legal person), 'name.full' and one identifier (POI of type id.*) must be included. All other pieces of information are optional, such as, birth.date, birth.place, nationality or address.
	@GetMapping("/")
	public ResponseEntity<String> getCustomersByAccountId(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.getCustomersByAccountId(requestJson);
	}

	// Make a search in customers. The search term will be matched against names and document ids of the customers.
	@PostMapping("/find")
	public ResponseEntity<String> findCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.findCustomer(requestJson);
	}

	// Returns all information (visible for the given company account) of a customer
	@GetMapping("/customer")
	public ResponseEntity<String> getCustomersByCustomerId(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.getCustomersByCustomerId(requestJson);
	}

	// Attivazione di un cliente
	@PostMapping("/activate")
	public ResponseEntity<String> activateCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.activateCustomer(requestJson);
	}

	// Returns all alerts related to a given customer
	@GetMapping("/alerts")
	public ResponseEntity<String> alertCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.alertCustomer(requestJson);
	}

	// Sets a new risk value. You can change the risk and add a comment. As response you will get the whole customer object.
	@PostMapping("/change-risk")
	public ResponseEntity<String> changeCustomerRisk(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.changeCustomerRisk(requestJson);
	}

	// Deactivates a customer and stops monitoring. You normally deactivate a customer when he/she is no longer your customer but want or need to keep the data.
	@PostMapping("/deactivate")
	public ResponseEntity<String> deactivateCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.deactivateCustomer(requestJson);
	}

	// Eliminazione di un cliente
	@PostMapping("/delete")
	public ResponseEntity<String> deleteCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.deleteCustomer(requestJson);
	}

	@GetMapping("/documents")
	public ResponseEntity<String> documentCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.documentCustomer(requestJson);
	}

	@GetMapping("/matches")
	public ResponseEntity<String> matchesCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.matchesCustomer(requestJson);
	}

	// Rifiutare tutti i match di un cliente
	@PostMapping("/reject-all")
	public ResponseEntity<String> rejectAllMatches(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.rejectAllMatches(requestJson);
	}

	// Accettare un match del cliente
	@PostMapping("/accept")
	public ResponseEntity<String> acceptMatch(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.acceptMatch(requestJson);
	}

	// Rifiutare un match del cliente
	@PostMapping("/reject")
	public ResponseEntity<String> rejectMatch(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.rejectMatch(requestJson);
	}

	// Aggiungere points of information (pois) per un cliente
	@PostMapping("/pois")
	public ResponseEntity<String> addCustomerPois(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.addCustomerPois(requestJson);
	}

	// Endpoint per ottenere un report di un soggetto
	@GetMapping("/report")
	public ResponseEntity<byte[]> getCustomerReport(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.getCustomerReport(requestJson);
	}
	
	
	


	// Nuovo metodo POST per inviare i dati dei clienti
	@PostMapping("/create")
	public ResponseEntity<String> createCustomer(@RequestBody AccountsSearchPojo requestJson) {
		return customersService.createCustomer(requestJson);
	}

}
