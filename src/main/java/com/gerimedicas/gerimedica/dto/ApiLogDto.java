package com.gerimedicas.gerimedica.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author volkanozturk
 */

@AllArgsConstructor
@Builder
@Data
public class ApiLogDto {
	private String sourceIpAddress;
	@NotNull
	private String httpRequestMethod;
	@NotNull
	private String endPoint;
	private String requestPayload;
	private String responsePayload;
	private Integer httpStatusCode;
	private Long totalDuration;
	private String exceptionMessage;
	private Date callDate;
}
