package it.monitoraggio.controller.pojo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UtentePojo {
	private Long idUtente;

	private Long fkIntermediario;
	private String username;
	private String password;
	private String email;
	private Long idRuolo;
	private String nome;
	private String cognome;
	private Timestamp dataNascita;
	private String codiceFiscale;
	private String stato;
//	privatefoto	blob	YES			
	private String foto_ext;
	private Timestamp dataCreazione;
	private Timestamp dataCessazione;
	private Timestamp dataAggiornamento;
	private List<IndirizziPojo> elencoIndirizzi = new ArrayList<IndirizziPojo>();

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public Long getFkIntermediario() {
		return fkIntermediario;
	}

	public void setFkIntermediario(Long fkIntermediario) {
		this.fkIntermediario = fkIntermediario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Timestamp getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getFoto_ext() {
		return foto_ext;
	}

	public void setFoto_ext(String foto_ext) {
		this.foto_ext = foto_ext;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Timestamp getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public List<IndirizziPojo> getElencoIndirizzi() {
		return elencoIndirizzi;
	}

	public void setElencoIndirizzi(List<IndirizziPojo> elencoIndirizzi) {
		this.elencoIndirizzi = elencoIndirizzi;
	}

}
