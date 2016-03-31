package com.oracle.aone.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -931150089709814744L;

	public UnAuthorizedException(Exception e) {
		super(e);
	}

	public UnAuthorizedException() {
		super();
	}

}
