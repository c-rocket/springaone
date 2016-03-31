package com.oracle.aone.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CREATED)
public class CreationErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7918193169712563211L;

	public CreationErrorException(Exception e) {
		super(e);
	}

}
