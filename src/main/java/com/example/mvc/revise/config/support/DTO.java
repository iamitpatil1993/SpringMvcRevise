package com.example.mvc.revise.config.support;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation will be used to annotate controller method parameters of
 * which we want to convert from request body DTO to Entity type
 * 
 * @author amipatil
 *
 */
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface DTO {

	/**
	 * DTO class type to which body to be parsed into. I.e what is type of request
	 * body
	 * 
	 * @return
	 */
	public Class<?> type();
}
