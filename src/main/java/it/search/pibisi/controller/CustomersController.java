package it.search.pibisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.common.pibisi.bean.MatchBean;
import it.common.pibisi.bean.MatchListBean;
import it.common.pibisi.controller.pojo.AccountsSearchPojo;
import it.common.pibisi.pojo.customers.create.CustomersCreateResponse;
import it.search.pibisi.service.CustomersCreateService;
import it.search.pibisi.service.CustomersFindByAccountService;
import it.search.pibisi.service.CustomersFindByCustomerService;
import it.search.pibisi.service.CustomersMatchService;
import it.search.pibisi.service.CustomersReportService;
import it.search.pibisi.service.CustomersService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping({ "/customers", "/api/customers" })
public class CustomersController {

	@Autowired
	private CustomersService customersService;

	@Autowired
	private CustomersMatchService customersMatchService;

	@Autowired
	private CustomersCreateService customersCreateService;

	@Autowired
	private CustomersFindByCustomerService customersFindByCustomerService;

	@Autowired
	private CustomersFindByAccountService customersFindByAccountService;

	@Autowired
	private CustomersReportService customersReportService;

	// Nuovo metodo POST per inviare i dati dei clienti
	@PostMapping("/create")
	public CustomersCreateResponse create(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersCreateService.create(requestJson, request);
	}

	// Returns all information (visible for the given company account) of a customer
	@PostMapping("/findByCustomer")
	public MatchBean findByCustomer(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersFindByCustomerService.findByCustomer(requestJson, request);
	}

	// Registers a new person (customer) to follow up. You can send multiple pieces
	// of information (aka. pois), but at least the types 'person' (allowed values:
	// 'P' for natural person, and 'E' for legal person), 'name.full' and one
	// identifier (POI of type id.*) must be included. All other pieces of
	// information are optional, such as, birth.date, birth.place, nationality or
	// address.
	@GetMapping("/findByAccount")
	public MatchListBean findByAccount(HttpServletRequest request) {
		return customersFindByAccountService.findByAccount(request);
	}

	@PostMapping("/matches")
	public MatchListBean matches(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersMatchService.matches(requestJson, request);
	}

	// Make a search in customers. The search term will be matched against names and
	// document ids of the customers.
	@PostMapping("/find")
	public ResponseEntity<String> find(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.find(requestJson, request);
	}

	// Attivazione di un cliente
	@PostMapping("/activate")
	public ResponseEntity<String> activate(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.activate(requestJson, request);
	}

	// Deactivates a customer and stops monitoring. You normally deactivate a
	// customer when he/she is no longer your customer but want or need to keep the
	// data.
	@PostMapping("/deactivate")
	public ResponseEntity<String> deactivate(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.deactivate(requestJson, request);
	}

	// Eliminazione di un cliente
	@PostMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.delete(requestJson, request);
	}

	// Returns all alerts related to a given customer
	@PostMapping("/alerts")
	public ResponseEntity<String> alert(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.alert(requestJson, request);
	}

	// Accettare un match del cliente
	@PostMapping("/accept")
	public ResponseEntity<String> accept(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.accept(requestJson, request);
	}

	// Rifiutare un match del cliente
	@PostMapping("/reject")
	public ResponseEntity<String> reject(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.reject(requestJson, request);
	}

	// Sets a new risk value. You can change the risk and add a comment. As response
	// you will get the whole customer object.
	@PostMapping("/change-risk")
	public ResponseEntity<String> changeRisk(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.changeRisk(requestJson, request);
	}

	@GetMapping("/documents")
	public ResponseEntity<String> documents(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.documents(requestJson, request);
	}

	// Rifiutare tutti i match di un cliente
	@PostMapping("/reject-all")
	public ResponseEntity<String> rejectAll(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.rejectAll(requestJson, request);
	}

	// Aggiungere points of information (pois) per un cliente
	@PostMapping("/pois")
	public ResponseEntity<String> addPois(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersService.addPois(requestJson, request);
	}

	// Endpoint per ottenere un report di un soggetto
	@PostMapping("/report")
	public byte[] report(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return customersReportService.createPdf(requestJson, request);
	}

}
