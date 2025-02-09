package it.search.pibisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import it.search.pibisi.controller.pojo.PibisiPojo;

@Component
public class BaseService {
	@Autowired
	private AccountsService accountsService;

	public String getAccountId(PibisiPojo requestJson) {
		if (StringUtils.hasText(requestJson.getAccountId())) {
			return requestJson.getAccountId();
		} else {

			return accountsService.getAccountId();
		}
	}

}
