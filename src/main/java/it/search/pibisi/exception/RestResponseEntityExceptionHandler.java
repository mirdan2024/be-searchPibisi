package it.search.pibisi.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.common.base.util.DateUtils;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class,
			SQLIntegrityConstraintViolationException.class, MyResourceNotFoundException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "This should be application specific";
		if (ex instanceof MyResourceNotFoundException) {
			bodyOfResponse = ((MyResourceNotFoundException) ex).getMessage();
			MyResourceNotFoundException myResourceNotFoundException = (MyResourceNotFoundException) ex;
			Error error = new Error();
			error.setCode(myResourceNotFoundException.getCodice());
			error.setDescription(myResourceNotFoundException.getDescrizione());

			return manageException(error, new org.springframework.http.HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		}
		return handleExceptionInternal(ex, bodyOfResponse, new org.springframework.http.HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	protected ResponseEntity<Object> manageException(Error errors, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", DateUtils.getNow());
		body.put("status", status.value());

		// Get all errors
//		List<String> errors = ex.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodValidationException(MethodValidationException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", DateUtils.getNow());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);
	}

}