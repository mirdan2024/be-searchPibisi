package it.search.pibisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.bean.PdfResponse;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.service.AccountsDetailService;
import it.search.pibisi.service.AccountsSearchService;
import it.search.pibisi.service.PdfService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping({ "/accounts", "/api/accounts" })
public class AccountsSearchController {

	@Autowired
	private AccountsSearchService searchService;

	@Autowired
	private AccountsDetailService detailService;

	@Autowired
	private PdfService pdfService;

	// Endpoint per la ricerca di un nominativo sulle liste
	@PostMapping("/search")
	public MatchListBean search(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return searchService.search(requestJson, request);
	}

	// Endpoint per il dettaglio di un nominativo trovato liste
	@PostMapping("/detail")
	public MatchBean detail(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		return detailService.detail(requestJson, request);
	}

	// Endpoint per il dettaglio di un nominativo trovato liste
	@PostMapping("/pdf")
	public PdfResponse createPdf(@RequestBody AccountsSearchPojo requestJson, HttpServletRequest request) {
		byte[] pdfBytes = pdfService.createPdf(requestJson, request);
		PdfResponse pdfResponse = new PdfResponse();
		pdfResponse.setPdf(pdfBytes);
		return pdfResponse;
	}
}
