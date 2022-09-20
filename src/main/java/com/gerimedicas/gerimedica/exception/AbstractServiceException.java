package com.gerimedicas.gerimedica.exception;

/**
 * @author volkanozturk
 */
public abstract class AbstractServiceException extends Exception {

	private final String code;

	private final Object data;

	private final String errorTitle;

	private final String errorDetail;

	protected AbstractServiceException(String code, String developerMessage) {
		this(code, developerMessage, null);
	}

	protected AbstractServiceException(String code, String developerMessage, Object data) {
		this(code, developerMessage, data, null, null);
	}

	protected AbstractServiceException(String code, String developerMessage, Object data,
									   String errorTitle, String errorDetail) {
		super(developerMessage);
		this.code = code;
		this.data = data;
		this.errorTitle = errorTitle;
		this.errorDetail = errorDetail;
	}

}
