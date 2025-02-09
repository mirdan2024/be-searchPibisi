package it.search.pibisi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.search.pibisi.service.AccountsService;

@Component
public class RunAfterStartup {

	@Autowired
	private AccountsService accountsService;

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		accountsService.getAccountId();
	}
}