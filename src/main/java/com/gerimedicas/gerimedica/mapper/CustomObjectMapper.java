package com.gerimedicas.gerimedica.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimeZone;

/**
 * @author volkanozturk
 */
public class CustomObjectMapper extends ObjectMapper {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	public CustomObjectMapper() {
		super.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		super.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		super.setTimeZone(TimeZone.getDefault());
	}

	public <T> T fromJson(String jsonString, Class<T> target) {
		try {
			if (jsonString != null) {
				return this.readValue(jsonString, target);
			}
		} catch (Exception e) {
			this.logger.error("fromJson, detail: ", e);
		}
		return null;
	}

	public String toJson(Object input) {
		try {
			if (input != null) {
				return this.writeValueAsString(input);
			}
		} catch (Exception e) {
			this.logger.error("toJson, detail: ", e);
		}
		return null;
	}
}
