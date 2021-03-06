package com.example.mvc.revise.web.controller;

import java.util.List;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller shows, how to use query parameter declaration in
 * {@link RequestMapping} annotation, and hw spring uses this declaration to
 * chose controller handler method based on,
 * <li>Query parameter available or not</li>
 * <li>Value of query parameter equal to something</li>
 * <li>Value of query parameter not equal to something</li>
 * 
 * Spring chose controller method based on query parameter availability and
 * value. So, if we have controller method whose logic differers a lot based on
 * availability of query parameter or value equal to or not equal to something
 * then we can writer two different controller methods to handle each such a
 * different case with exactly same resource URI path. This will help us to
 * separate out different logic in each case.
 * 
 * @author amipatil
 *
 */

@RestController
public class RequestParameterTestController {

	private static final String QUERY_PARAMS_TEST_PATH = "/query-params/test";

	/**
	 * Since there are multiple handler for same path, this handler will get called
	 * as fallback, when 1. Value for both param missing 2. Value for any one of two
	 * param is missing
	 */
	@GetMapping(path = QUERY_PARAMS_TEST_PATH)
	private String withoutQueryParameters(@RequestParam(name = "fooParam", required = false) String fooParam,
			@RequestParam(name = "barParam", required = false) String barParam) {
		return getResultString(fooParam, barParam);
	}

	/**
	 * This method will get called when both "fooParam", "barParam" param values are
	 * available in request. If one of two params missing, spring will call
	 * {@link RequestParameterTestController#withoutQueryParameters(String, String)}
	 * 
	 * Declaring params in {@link RequestMapping} and related annotations, will make
	 * them required, and hence spring will not call method if any of parameter is
	 * missing, spring will return 400 Bad request. NOTE: adding Required = false
	 * in @RequestParam will have no effect now. Hence this method will get called
	 * only where two query params are passed (even though {@link RequestParam}
	 * annotation declares to be optional) otherwise spring search for other methof
	 * with same Http method and path and can be called without parameters.
	 * 
	 */
	@GetMapping(path = QUERY_PARAMS_TEST_PATH, params = { "fooParam", "barParam" })
	private String withQueryParameters(@RequestParam(name = "fooParam", required = false) String fooParam,
			@RequestParam(name = "barParam", required = false) String barParam) {
		return getResultString(fooParam, barParam);
	}

	/**
	 * This handler will get only called when both of param values are present in
	 * request and their values matches as declared below in declaration, in any
	 * other case this handler will not get called
	 */
	@GetMapping(path = QUERY_PARAMS_TEST_PATH, params = { "fooParam=fooValue", "barParam=barValue" })
	private String withQueryParamsValueMatching(@RequestParam(name = "fooParam") String fooParam,
			@RequestParam(name = "barParam") String barParam) {
		return getResultString(fooParam, barParam);
	}

	/**
	 * This handler will get only called when both of param values are present in
	 * request and their values matches as declared below in declaration, in any
	 * other case this handler will not get called. This will get called only when
	 * "fooParam=fooValue", "barParam!=barValue"
	 */
	@GetMapping(path = QUERY_PARAMS_TEST_PATH, params = { "fooParam=fooValue", "barParam!=barValue" })
	private String withQueryParamsValueMatching2(@RequestParam(name = "fooParam") String fooParam,
			@RequestParam(name = "barParam") String barParam) {
		return getResultString(fooParam, barParam);
	}

	private String getResultString(String fooParam, String barParam) {
		return String.format("Called %s with fooParam :: %s and barParam :: %s",
				new Throwable().getStackTrace()[1].getMethodName(), fooParam, barParam);
	}

	/**
	 * Spring supports multi-value query parameters out of the box. We can send
	 * multiple values for query parameter either comma separated or multiple
	 * key-value pair of query param. Spring also parse each query parameter value
	 * to collection, and if value of one of query parameter is not valid Bad
	 * Request is send to client
	 * 
	 * @param params
	 * @param intParams
	 * @return
	 */
	@GetMapping(path = QUERY_PARAMS_TEST_PATH, params = { "param", "intParam" })
	public String multiValueRequestParameter(final @RequestParam(name = "param") List<String> params,
			final @RequestParam(name = "intParam") List<Integer> intParams) {
		return String.format(
				"Multi value params are supported in spring as a comma separated along with parsing of each param to appropriate declared type, params :: %s, intParams :: %s",
				params, intParams);
	}

	/**
	 * If we call this with '/path-param/test/spring-web-3.3.4.jar' then version
	 * will be 3.3.4, name will be spring-web and ext will be .jar So, we can create
	 * multiple path variables in url path by parsing single path element.
	 * 
	 * @param version extracted as a path variable for url path
	 *                /spring-web-3.3.4.jar
	 * @param ext     extracted as a path variable for url path
	 *                /spring-web-3.3.4.jar
	 * @param name    extracted as a path variable for url path
	 *                /spring-web-3.3.4.jar
	 * @return
	 */
	@GetMapping("/path-param/test/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
	public String handle(@PathVariable String version, @PathVariable String ext, @PathVariable String name) {
		return String.format("Version is :: %s and file extension is :: %s, name of artifact is :: %s", version, ext,
				name);
	}
	
	/**
	 * URI path patterns can also have embedded ${…​} placeholders that are resolved
	 * on startup by using PropertyPlaceHolderConfigurer against local, system,
	 * environment, and other property sources. You can use this, for example, to
	 * parameterize a base URL based on some external configuration.
	 * 
	 * NOTE: This API and above one has same url and completely depends on value of variable values,
	 * so spring applies pattern matching using {@link AntPathMatcher#getPatternComparator(String)} to determine 
	 * best or most suitable handler for url.
	 * So, if we send request to '/path-param/test/spring-web-3.3.4.jar' spring will call above handler and if 
	 * we send request to  '/path-param/test/v1' then spring will choose this handler. Great ha.
	 * 
	 * @param appVersion user must send appVersion which is active and configured as
	 *                   a property placeholder using app.properties
	 * @return
	 */
	@GetMapping(path = { "/path-param/test/{appVersion:${api.version}}" })
	public String placeHolderBasedPathVariable(@PathVariable String appVersion) {
		return String.format("Current API version is %s", appVersion);
	}
	
}
