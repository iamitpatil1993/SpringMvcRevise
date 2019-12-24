package com.example.mvc.revise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.example.mvc.revise.config.web.WebConfiguration;

@Configuration
@Import({ WebConfiguration.class, RootConfiguration.class })
public class SpringConfiguration {
	// Nothing to do here for now

	// You need this
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
