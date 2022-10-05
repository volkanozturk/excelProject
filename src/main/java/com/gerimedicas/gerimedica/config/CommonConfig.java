package com.gerimedicas.gerimedica.config;

import com.gerimedicas.gerimedica.mapper.CustomObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author volkanozturk
 */
@Configuration
public class CommonConfig {

	@Bean(name = "customObjectMapper")
	public CustomObjectMapper customObjectMapper() {
		return new CustomObjectMapper();
	}
}
