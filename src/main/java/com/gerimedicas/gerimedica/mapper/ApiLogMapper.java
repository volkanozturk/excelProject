package com.gerimedicas.gerimedica.mapper;

import com.gerimedicas.gerimedica.dto.ApiLogDto;
import com.gerimedicas.gerimedica.entity.ApiLog;

/**
 * @author volkanozturk
 */
public class ApiLogMapper {

	public static ApiLogDto toDto(ApiLog apiLog) {
		return ApiLogDto.builder()
				.endPoint(apiLog.getEndPoint())
				.exceptionMessage(apiLog.getExceptionMessage())
				.httpRequestMethod(apiLog.getHttpRequestMethod())
				.httpStatusCode(apiLog.getHttpStatusCode())
				.callDate(apiLog.getCallDate())
				.requestPayload(apiLog.getRequestPayload())
				.responsePayload(apiLog.getResponsePayload())
				.sourceIpAddress(apiLog.getSourceIpAddress())
				.totalDuration(apiLog.getTotalDuration())
				.exceptionMessage(apiLog.getExceptionMessage())
				.build();
	}

	public static ApiLog toEntity(ApiLogDto apiLogDto){
	return ApiLog.builder()
				.endPoint(apiLogDto.getEndPoint())
				.exceptionMessage(apiLogDto.getExceptionMessage())
				.httpRequestMethod(apiLogDto.getHttpRequestMethod())
				.httpStatusCode(apiLogDto.getHttpStatusCode())
				.callDate(apiLogDto.getCallDate())
				.requestPayload(apiLogDto.getRequestPayload())
				.responsePayload(apiLogDto.getResponsePayload())
				.sourceIpAddress(apiLogDto.getSourceIpAddress())
				.totalDuration(apiLogDto.getTotalDuration())
				.exceptionMessage(apiLogDto.getExceptionMessage())
				.build();
	}
}
