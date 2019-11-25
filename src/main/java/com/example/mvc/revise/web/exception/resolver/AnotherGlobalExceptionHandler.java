package com.example.mvc.revise.web.exception.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mvc.revise.dto.JsonResponse;
import com.example.mvc.revise.web.controller.ResouseNotFoundException;
import com.example.mvc.revise.web.controller.SpittleAlreadyExitstException;

/**
 * We can use {@link ControllerAdvice} to define global exception handler for
 * all controllers. We can control controllers to be supported using annotation
 * parameters of
 * 
 * These exception handlers also get invoked if exception is thrown from
 * HandlerInterceptors.
 * 
 * @author amipatil
 *
 */
@ControllerAdvice
/**
 * if we have multiple Exception handlers in application then spring will
 * randomly chose first matching exception handler method for exception. Hence
 * we can order our exception handlers so that we can define more specific
 * exception handler in high priority ExceptionHandler and generic/base
 * exception handler in lower order ExceptionHandler as a fallback
 * ExceptionHandlers. Read {@link ControllerAdvice} for more information.
 * 
 * NOTE: if we have generic/base exception class handler in high order
 * ExceptionHandler class then spring will chose that one even if we have
 * ExceptionHandler for that specific exception in lower order ExceptionHandler.
 * 
 * @author amipatil
 *
 */
@Order(value = 1)
public class AnotherGlobalExceptionHandler extends BaseGlobalExceptionHandler {

	/**
	 * We can send any response as controller from exception handler.
	 * 
	 * @param alreadyExitstException
	 * @param model
	 * @param httpServletRequest
	 * @return
	 */
	@ExceptionHandler(value = SpittleAlreadyExitstException.class)
	public String spittleAlreadyExistsExceptionHandler(SpittleAlreadyExitstException alreadyExitstException,
			final Model model, HttpServletRequest httpServletRequest) {
		super.logRequest(httpServletRequest);
		System.out.println("Inside GlobalExceptionHandler#spittleAlreadyExistsExceptionHandler");
		model.addAttribute("message", alreadyExitstException.getMessage());
		return "errors/notFound";
	}

	@ExceptionHandler(value = Exception.class)
	public String genericExceptionHandler(Exception exception, Model model, HttpServletRequest httpServletRequest) {
		super.logRequest(httpServletRequest);
		System.out.println("Inside GlobalExceptionHandler#genericExceptionHandler");
		model.addAttribute("message", "Internal Server Error caused by, : " + exception.getMessage());
		exception.printStackTrace();
		return "/errors/notFound";
	}
	
	/**
	 * Exception handler can be implemented in exactly similar way as a controller method.
	 */
	@ExceptionHandler(value = ResouseNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody JsonResponse resoureNotFoundExceptionHandler(ResouseNotFoundException exception) {
		return new JsonResponse().setHttpStatus(HttpStatus.NOT_FOUND).setMessage(exception.getMessage());
	}
}
