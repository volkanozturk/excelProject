package com.gerimedicas.gerimedica.aspect;

import com.gerimedicas.gerimedica.annotation.ApiLogger;
import com.gerimedicas.gerimedica.dto.ApiLogDto;
import com.gerimedicas.gerimedica.mapper.ApiLogMapper;
import com.gerimedicas.gerimedica.mapper.CustomObjectMapper;
import com.gerimedicas.gerimedica.repository.ApiLogRepository;
import com.gerimedicas.gerimedica.utils.CommonUtility;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * @author volkanozturk
 */
@Aspect
@Component
public class ApiLogAspect {

	private final ApiLogRepository apiLogRepository;

	@Qualifier(value = "customObjectMapper")
	private final CustomObjectMapper customObjectMapper;

	public ApiLogAspect(ApiLogRepository apiLogRepository, CustomObjectMapper customObjectMapper) {
		this.apiLogRepository = apiLogRepository;
		this.customObjectMapper = customObjectMapper;
	}


	@Around("@annotation(apiLogger)")
	public Object log(ProceedingJoinPoint joinPoint, ApiLogger apiLogger) throws Throwable {
		HttpServletRequest request = CommonUtility.getHttpServletRequest();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			Object response = joinPoint.proceed();
			stopWatch.stop();
			String responseToJson =this.customObjectMapper.toJson(response);
			this.saveApiLog(this.generateApiLog(joinPoint, request, stopWatch, responseToJson, null));
			return response;
		} catch (Exception e) {
			stopWatch.stop();
			String errorCode = "";
			if (RequestContextHolder.getRequestAttributes() != null) {
				RequestContextHolder.getRequestAttributes().setAttribute("errorCode", errorCode, RequestAttributes.SCOPE_REQUEST);
			}
			this.generateApiLog(joinPoint, request, stopWatch, errorCode, e.getMessage());
			throw e;
		}
	}

	private void saveApiLog(ApiLogDto apiLogDto) {
		this.apiLogRepository.save(ApiLogMapper.toEntity(apiLogDto));
	}
	private ApiLogDto generateApiLog(ProceedingJoinPoint joinPoint, HttpServletRequest request, StopWatch stopWatch, String responseToJson, String exceptionMessage) {
		return ApiLogDto.builder()
				.sourceIpAddress(CommonUtility.getIpAddress(request))
				.httpRequestMethod(request.getMethod())
				.endPoint(request.getRequestURL().toString())
				.requestPayload(CommonUtility.generatePayloadDetail(joinPoint))
				.responsePayload(responseToJson)
				.httpStatusCode(HttpStatus.OK.value())
				.totalDuration(stopWatch.getTotalTimeMillis())
				.exceptionMessage(exceptionMessage)
				.callDate(Calendar.getInstance().getTime())
				.build();
	}
}
