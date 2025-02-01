package it.monitoraggio.controller.pojo;

import java.sql.Timestamp;

public class TipoIntermediarioPojo {

	private Long idTipoIntermediario;
	private String descrizione;
	private String categoria;
	private String livelloRischio;
	private String note;
	private Timestamp dataCreazione;
	private Timestamp dataCessazione;

	public Long getIdTipoIntermediario() {
		return idTipoIntermediario;
	}

	public void setIdTipoIntermediario(Long idTipoIntermediario) {
		this.idTipoIntermediario = idTipoIntermediario;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getLivelloRischio() {
		return livelloRischio;
	}

	public void setLivelloRischio(String livelloRischio) {
		this.livelloRischio = livelloRischio;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

}
