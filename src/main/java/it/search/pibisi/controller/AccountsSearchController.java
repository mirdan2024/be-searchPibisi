package it.search.pibisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.MatchListBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import it.search.pibisi.service.AccountsSearchService;

@RestController
public class AccountsSearchController {

	@Autowired
	private AccountsSearchService searchService;

	// Endpoint per la ricerca di un nominativo sulle liste
	@PostMapping("/accounts/search")
	public MatchListBean search(@RequestBody AccountsSearchPojo requestJson) {
		return searchService.search(requestJson);
	}

	// Endpoint per il dettaglio di un nominativo trovato liste
	@PostMapping("/accounts/detail")
	public MatchBean detail(@RequestBody AccountsSearchPojo requestJson) {
		return searchService.detail(requestJson);
	}
}
