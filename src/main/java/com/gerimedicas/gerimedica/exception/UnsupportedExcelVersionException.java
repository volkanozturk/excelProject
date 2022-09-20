package com.gerimedicas.gerimedica.exception;

/**
 * @author volkanozturk
 */
public class UnsupportedExcelVersionException extends AbstractServiceException{

	protected UnsupportedExcelVersionException(String code, String developerMessage) {
		super(code, developerMessage);
	}

	public UnsupportedExcelVersionException() {
		super("60000","You can only upload Excel files with .xlsx extension!");
	}
}
