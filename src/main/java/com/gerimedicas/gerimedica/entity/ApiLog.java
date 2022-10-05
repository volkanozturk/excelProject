package com.gerimedicas.gerimedica.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author volkanozturk
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class ApiLog extends Line {

	private String sourceIpAddress;
	private String httpRequestMethod;
	private String endPoint;
	private String requestPayload;
	private String responsePayload;
	private Integer httpStatusCode;
	private Long totalDuration;
	private String exceptionMessage;
	private Date callDate;
}
