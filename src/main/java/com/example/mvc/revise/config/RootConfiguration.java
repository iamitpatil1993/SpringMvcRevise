package com.example.mvc.revise.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Never include [by mistake] web components in this root configuration. I had
 * issue in component scan here, and HandlerInterceptors I declared were part of
 * both web and root config, hence they were getting called two times for each
 * request. So, selectively include packages for Root context or selectively
 * exclude web packages from root context.
 * 
 * @author amipatil
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.example.mvc.revise.dao" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class RootConfiguration {
}
