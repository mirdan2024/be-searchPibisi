package it.search.pibisi.pojo.users.me.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("config")
	private Config config;
	@JsonProperty("alias")
	private String alias;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
