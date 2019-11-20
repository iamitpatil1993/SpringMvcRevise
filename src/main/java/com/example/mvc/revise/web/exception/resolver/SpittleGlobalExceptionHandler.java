package com.example.mvc.revise.web.exception.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mvc.revise.web.controller.SpittleAlreadyExitstException;
import com.example.mvc.revise.web.controller.SpittleNotFoundException;

/**
 * When we define multiple ControllerAdvice for exception handling, behavior is
 * something different 1. One of them will be randomly chose as a first advice
 * to consult 2. If we define ordering, then advices will be consulted in
 * defined order 3. Exception handler in higher order advice will be preferred
 * over advice in lower order, hence even though no exact exception found
 * specific to exception, generic exception handler will be used. So, we should
 * always define generic or base exception class exception handlers in low order
 * Controller Advices
 * 
 * @author amipatil
 *
 */
@ControllerAdvice
@Order(value = 0)
public class SpittleGlobalExceptionHandler extends BaseGlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")
	@ExceptionHandler(value = SpittleNotFoundException.class)
	public void spittlrNotFoundExceptionHandler(SpittleNotFoundException exception, HttpServletRequest httpServletRequest) {
		super.logRequest(httpServletRequest);
		System.out.println("Inside GlobalExceptionHandler#spittlrNotFoundExceptionHandler");
	}

}
