package it.monitoraggio.controller.pojo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IntermediarioPojo {

	private Long idIntermediario;
	private String nominativo;
	private String codiceFiscale;
	private String partitaIva;
	private String codiceSDI;
	private Long tipoIntermediario;
	private String telefono;
	private String email;
	private String emailPec;
	private String logo;
	private String logoExt;
	private String apikey;
	private Timestamp dataCreazione;
	private Timestamp dataCessazione;
	private Timestamp dataAggiornamento;
	private TipoIntermediarioPojo tipoIntermediarioPojo;
	private List<IndirizziPojo> elencoIndirizzi = new ArrayList<IndirizziPojo>();
	private Long idLingua;

	public List<IndirizziPojo> getElencoIndirizzi() {
		return elencoIndirizzi;
	}

	public void setElencoIndirizzi(List<IndirizziPojo> elencoIndirizzi) {
		this.elencoIndirizzi = elencoIndirizzi;
	}

	public TipoIntermediarioPojo getTipoIntermediarioPojo() {
		if (tipoIntermediarioPojo == null)
			tipoIntermediarioPojo = new TipoIntermediarioPojo();
		return tipoIntermediarioPojo;
	}

	public void setTipoIntermediarioPojo(TipoIntermediarioPojo tipoIntermediarioPojo) {
		this.tipoIntermediarioPojo = tipoIntermediarioPojo;
	}

	public Long getIdIntermediario() {
		return idIntermediario;
	}

	public void setIdIntermediario(Long idIntermediario) {
		this.idIntermediario = idIntermediario;
	}

	public String getNominativo() {
		return nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getCodiceSDI() {
		return codiceSDI;
	}

	public void setCodiceSDI(String codiceSDI) {
		this.codiceSDI = codiceSDI;
	}

	public Long getTipoIntermediario() {
		return tipoIntermediario;
	}

	public void setTipoIntermediario(Long tipoIntermediario) {
		this.tipoIntermediario = tipoIntermediario;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailPec() {
		return emailPec;
	}

	public void setEmailPec(String emailPec) {
		this.emailPec = emailPec;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogoExt() {
		return logoExt;
	}

	public void setLogoExt(String logoExt) {
		this.logoExt = logoExt;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
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

	public Long getIdLingua() {
		return idLingua;
	}

	public void setIdLingua(Long idLingua) {
		this.idLingua = idLingua;
	}


}
