package com.gerimedicas.gerimedica.exception;

/**
 * @author volkanozturk
 */
public class ExcelNotFoundException extends AbstractServiceException {
	public ExcelNotFoundException() {
		super("6001", "Excel could not be found.");
	}
}
