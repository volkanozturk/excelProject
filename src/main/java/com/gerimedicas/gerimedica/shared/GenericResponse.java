package com.gerimedicas.gerimedica.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author volkanozturk
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenericResponse<T> {

	private static final GenericResponse DEFAULT_SUCCESS = successful(null);

	public static GenericResponse successful() {
		return DEFAULT_SUCCESS;
	}

	private Boolean successful;
	private Integer status;
	private String message;
	private T data;

	public static <T> GenericResponse<T> successful(T data) {
		GenericResponse<T> genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(true);
		genericResponse.setStatus(HttpStatus.OK.value());
		genericResponse.setData(data);
		return genericResponse;
	}

	public static <T> GenericResponse<T> failure(Integer status) {
		GenericResponse<T> genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(false);
		genericResponse.setStatus(status);
		return genericResponse;
	}

}
