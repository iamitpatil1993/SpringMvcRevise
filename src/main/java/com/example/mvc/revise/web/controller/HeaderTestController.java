package com.example.mvc.revise.web.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderTestController {

	/**
	 * This is how we can read specific header from request, similar to
	 * RequestParam, RequestHeader is also mandatory by default and hence spring
	 * will throw Bad Request error code to client if header value is missing.
	 * 
	 * We can make it optional using {@link RequestHeader#required} attribute. We
	 * can map header to specific data type as well
	 * 
	 * @param userAgent User-Agent header
	 */
	@GetMapping(path = "/headers/specific/test")
	public String specificHeader(@RequestHeader(name = "User-Agent", required = false) String userAgent,
			@RequestHeader(name = "Content-Length", required = false) Long contentLength) {
		System.out.println("User-Agent header is :: " + userAgent);
		System.out.println("Content-Length header is :: " + contentLength);
		return "Header value was :: " + userAgent;
	}

	/**
	 * This is how we can read all headers using java.util.Map Issue with
	 * java.util.Map is, if some header is multi-value and client sends multiple
	 * values for header then, Map value will be first value out of multiple for
	 * that header
	 * 
	 * @param headers
	 */
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@GetMapping(path = "/headers/all/map")
	public void allHeadersUsingMap(@RequestHeader Map<String, String> headers) {
		headers.forEach((key, value) -> {
			System.out.println(String.format("Header Key :: %s, value :: %s", key, value));
		});
	}

	/**
	 * This is how we can get all headers with all values for each headers. Using
	 * {@link MultiValueMap}
	 * 
	 * @param headers
	 */
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@GetMapping(path = "/headers/all/multi-value-map")
	public void allHeadersUsingMultiValueMap(@RequestHeader MultiValueMap<String, String> headers) {
		headers.forEach((key, value) -> {
			System.out.println(String.format("Header Key :: %s, values :: %s", key,
					value.stream().collect(Collectors.joining("|"))));
		});
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@GetMapping(path = "/headers/all/http-headers")
	public void allHeadersUsingHttpHeader(@RequestHeader HttpHeaders headers) {
		System.out.println("headers.getHost() :: " + headers.getHost()); // we can access standard headers via methods
		System.out.println("User-Agent :: " + headers.get("User-Agent")); // we can access custom/non-standard headers
		// like this.
	}

}
