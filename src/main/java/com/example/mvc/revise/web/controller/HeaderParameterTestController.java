package com.example.mvc.revise.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/header-params/test" })
public class HeaderParameterTestController {

	/**
	 * With optional header
	 * 
	 * @param fooBarHeaderValue
	 * @return
	 */
	@GetMapping
	public String withoutHeader(@RequestHeader(name = "fooBar", required = false) String fooBarHeaderValue) {
		return String.format("called withoutHeader with header value :: %s", fooBarHeaderValue);
	}

	/**
	 * There are two handlers candidates to handle request with fooBar header, one
	 * with optional header
	 * {@link HeaderParameterTestController#withoutHeader(String)} and this, but
	 * spring handles very smartly and chose most matching handler. Spring selects
	 * this handler when request has fooBar header, this is because, this methods
	 * requires fooBar header and header is optional in above method so this method
	 * is more specific candidate.
	 * 
	 * @param fooBarHeaderValue
	 * @return
	 */
	@GetMapping(headers = { "fooBar" })
	public String withHeaderRequiredToProcessRequest(@RequestHeader(name = "fooBar") String fooBarHeaderValue) {
		return String.format("called withHeaderRequiredToProcessRequest with header value :: %s", fooBarHeaderValue);
	}

	/**
	 * Spring selects this method only when header value exists and it's value is
	 * equal to fooBarValue, if value is not equal to fooBarValue, only above two
	 * candidates are considered.
	 * 
	 * @param fooBarHeaderValue
	 * @return
	 */
	@GetMapping(headers = { "fooBar=fooBarValue" })
	public String withHeaderRequiredToProcessRequestWithSpecificValue(
			@RequestHeader(name = "fooBar") String fooBarHeaderValue) {
		return String.format("called withHeaderRequiredToProcessRequestWithSpecificValue with header value :: %s",
				fooBarHeaderValue);
	}

}
