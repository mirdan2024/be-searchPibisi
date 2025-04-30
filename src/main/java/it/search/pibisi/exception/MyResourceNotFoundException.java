package it.search.pibisi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MyResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codice;
	private String descrizione;

	public MyResourceNotFoundException() {
		super();
	}

	public MyResourceNotFoundException(String codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public MyResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyResourceNotFoundException(String message) {
		super(message);
	}

	public MyResourceNotFoundException(Throwable cause) {
		super(cause);
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
