package com.example.mvc.revise.web.exception.resolver;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.mvc.revise.dto.JsonResponse;
import com.example.mvc.revise.web.controller.ResouseNotFoundException;

@RestControllerAdvice // This is @ControllerAdvice + @ResponseBody, so we do not need to use @ResponseBody on exception handler methods.
@Order(value = 1)
public class RestGlobalExceptionHandler {
	
	/**
	 * Exception handler can be implemented in exactly similar way as a controller method.
	 * NOTE: We do not need to add @ResponseBody here
	 */
	@ExceptionHandler(value = ResouseNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public JsonResponse resoureNotFoundExceptionHandler(ResouseNotFoundException exception) {
		return new JsonResponse().setHttpStatus(HttpStatus.NOT_FOUND).setMessage(exception.getMessage());
	}

}
