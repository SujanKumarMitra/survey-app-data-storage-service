package com.github.mitrakumarsujan.datastorageservice.exception;

import static java.util.Collections.singletonList;

import java.util.Collection;

import com.github.mitrakumarsujan.formmodel.exception.ClientErrorException;
import com.github.mitrakumarsujan.formmodel.model.restresponse.error.ErrorInfo;
import com.github.mitrakumarsujan.formmodel.model.restresponse.error.ErrorInfoImpl;

/**
 * @author skmitra
 * @since 2020-11-08
 */
public class IncorrectCredentialsException extends ClientErrorException {

	
	private static final long serialVersionUID = -6555018822234704190L;
	
	private static final String DEFAULT_MESSAGE = "Incorrect Credentials";

	public IncorrectCredentialsException() {
		this(singletonList(new ErrorInfoImpl(DEFAULT_MESSAGE)));
	}

	public IncorrectCredentialsException(String message) {
		this(singletonList(new ErrorInfoImpl(message)));
	}

	public IncorrectCredentialsException(Collection<ErrorInfo> errors) {
		super(errors);
	}

}
