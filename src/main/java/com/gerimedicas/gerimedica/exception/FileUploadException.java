package com.gerimedicas.gerimedica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author volkanozturk
 */
public class FileUploadException extends RuntimeException {
	public FileUploadException(String message) {
		super(message);
	}
}
