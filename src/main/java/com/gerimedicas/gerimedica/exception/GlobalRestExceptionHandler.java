package com.gerimedicas.gerimedica.exception;

import com.gerimedicas.gerimedica.shared.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author volkanozturk
 */
@ControllerAdvice
public class GlobalRestExceptionHandler {

	@ExceptionHandler(value = {AbstractServiceException.class})
	public ResponseEntity<GenericResponse> handleException(AbstractServiceException ex) {
		return responseEntity(GenericResponse.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.successful(false)
				.message(ex.getMessage())
				.build());
	}

	@ExceptionHandler(value = {FileUploadException.class})
	public ResponseEntity<GenericResponse> handleFileUploadException(FileUploadException ex) {
		return responseEntity(GenericResponse.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.successful(false)
				.message(ex.getMessage())
				.build());
	}

	@ExceptionHandler(value = {ExcelNotFoundException.class})
	public ResponseEntity<GenericResponse> handleExcelNotFoundException(ExcelNotFoundException ex) {
		return responseEntity(GenericResponse.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.successful(false)
				.message("Excel could not be found")
				.build());
	}

	@ExceptionHandler(value = {UnsupportedExcelVersionException.class})
	public ResponseEntity<GenericResponse> handleUnsupportedExcelVersionException(UnsupportedExcelVersionException ex) {
		return responseEntity(GenericResponse.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.successful(false)
				.message(ex.getMessage())
				.build());
	}

	@ExceptionHandler(value = {MaxUploadSizeExceededException.class})
	public ResponseEntity<GenericResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
		return responseEntity(GenericResponse.builder()
				.status(HttpStatus.EXPECTATION_FAILED.value())
				.successful(false)
				.message("File too large!")
				.build());
	}


	private ResponseEntity<GenericResponse> responseEntity(GenericResponse error) {
		return new ResponseEntity(error, HttpStatus.valueOf(error.getStatus()));

	}


}
