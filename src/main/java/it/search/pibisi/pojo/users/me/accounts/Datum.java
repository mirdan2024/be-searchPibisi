package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Datum {

	@JsonProperty("role")
	private String role;
	@JsonProperty("account")
	private Account account;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
