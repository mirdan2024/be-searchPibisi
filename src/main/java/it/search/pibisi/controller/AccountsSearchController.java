package it.search.pibisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.service.AccountsDetailService;
import it.search.pibisi.service.AccountsSearchService;
import it.search.pibisi.service.PdfService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AccountsSearchController {

	@Autowired
	private AccountsSearchService searchService;
	
	@Autowired
	private AccountsDetailService detailService;
	
	@Autowired
	private PdfService pdfService;

	// Endpoint per la ricerca di un nominativo sulle liste
	@PostMapping("/accounts/search")
	public MatchListBean search(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return searchService.search(requestJson, request);
	}

	// Endpoint per il dettaglio di un nominativo trovato liste
	@PostMapping("/accounts/detail")
	public MatchBean detail(@RequestBody AccountsSearchPojo requestJson) {
		return detailService.detail(requestJson);
	}

	// Endpoint per il dettaglio di un nominativo trovato liste
	@PostMapping("/accounts/pdf")
	public void createPdf(@RequestBody AccountsSearchPojo requestJson) {
		pdfService.createPdf(requestJson);
	}
}
