package it.monitoraggio.controller.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IndirizziPojo {

	private Long idIndirizzo;

	private Long idIntermediario;

	private Long idUtente;

	private Long fkToponimo;
	private String descrizioneToponimo;

	private String indirizzo;

	private String numeroCivico;

	private String fkComune;
	private String descrizioneComune;

	private String fkPaese;
	private String descrizionePaese;

	private String descrizioneComuneEstero;

	private String cap;

	private String tipoIndirizzo;

	public Long getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}

	public Long getIdIntermediario() {
		return idIntermediario;
	}

	public void setIdIntermediario(Long idIntermediario) {
		this.idIntermediario = idIntermediario;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public Long getFkToponimo() {
		return fkToponimo;
	}

	public void setFkToponimo(Long fkToponimo) {
		this.fkToponimo = fkToponimo;
	}

	public String getDescrizioneToponimo() {
		return descrizioneToponimo;
	}

	public void setDescrizioneToponimo(String descrizioneToponimo) {
		this.descrizioneToponimo = descrizioneToponimo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getFkComune() {
		return fkComune;
	}

	public void setFkComune(String fkComune) {
		this.fkComune = fkComune;
	}

	public String getDescrizioneComune() {
		return descrizioneComune;
	}

	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}

	public String getFkPaese() {
		return fkPaese;
	}

	public void setFkPaese(String fkPaese) {
		this.fkPaese = fkPaese;
	}

	public String getDescrizionePaese() {
		return descrizionePaese;
	}

	public void setDescrizionePaese(String descrizionePaese) {
		this.descrizionePaese = descrizionePaese;
	}

	public String getDescrizioneComuneEstero() {
		return descrizioneComuneEstero;
	}

	public void setDescrizioneComuneEstero(String descrizioneComuneEstero) {
		this.descrizioneComuneEstero = descrizioneComuneEstero;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}

	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}

}
