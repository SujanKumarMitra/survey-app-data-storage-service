package com.github.mitrakumarsujan.datastorageservice.controlleradvice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.mitrakumarsujan.formmodel.exception.IncorrectCredentialsException;
import com.github.mitrakumarsujan.formmodel.model.restresponse.RestErrorResponse;
import com.github.mitrakumarsujan.formmodel.model.restresponse.error.RestErrorResponseBuilderFactory;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-11-02
 */
@RestControllerAdvice
public class IncorrectCredentialsExceptionHandler {

	@Autowired
	private RestErrorResponseBuilderFactory builderFactory;

	@ExceptionHandler(IncorrectCredentialsException.class)
	public ResponseEntity<RestErrorResponse> handle(IncorrectCredentialsException exception) {
		return builderFactory	.getErrorBuilder()
								.withStatus(HttpStatus.NOT_FOUND)
								.withErrors(exception.getErrors())
								.withMessage(exception.getMessage())
								.build()
								.toResponseEntity();
	}

}
