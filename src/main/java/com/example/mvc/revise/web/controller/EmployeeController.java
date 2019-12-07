package com.example.mvc.revise.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.mvc.revise.dto.Employee;
import com.example.mvc.revise.dto.JsonResponse;

@RestController
@RequestMapping(path = "/api/employees", produces = {"application/json", "application/xml"}) // applicable for all methods
//we can have this at controller level which will enable CROS for all handle methods, annotation at method level will add up to existing configuration here.
// single value attributes like maxAge will get overriden by method level annotation and collection attribute will be union or sum of controller level and method level.
//@CrossOrigin 
public class EmployeeController {

	private static final String HOME_AMIPATIL_TEMO = "/home/amipatil/temp/";

	/**
	 * produces, restricts type of response types this method can produce. If client requests mime type other than
	 * mentioned in this list, spring will throw 406 http error code, even though HttpMessageConverter compatible 
	 * for that type is registered in application context and appropriate library is also available in class-path
	 * @param employeeId
	 * @return
	 */
	// This is how we can enable CROS at handler method level (fine-grained) This enables specified origins and headers.
	// if we do not specify origin and headers, and only specify @CrossOrigin annotation, then spring will allow all origins and all headers
	// Methods allowed defaults to @RequestMapping annotation method, so here method allowed is GET only
	@CrossOrigin(origins = "http://localhost:8088", allowedHeaders = {"custom-header-1"})
	@GetMapping(path = {"/{employeeId}"})
	public ResponseEntity<JsonResponse> get(final @PathVariable String employeeId) {
		if (employeeId.toLowerCase().equals("notfound")) {
			throw new ResouseNotFoundException("employee not found by ID :: " + employeeId);
		}
		Employee employee = new Employee();
		employee.setFirstName("Amit");
		employee.setLastName("Patil");
		employee.setId(employeeId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new JsonResponse().setData(employee).setHttpStatus(HttpStatus.OK).setMessage("Success"));
	}

	@GetMapping
	@CrossOrigin(allowedHeaders = {"customHeader"}, origins = {"http://localhost:8080"})
	// we can use this to send status code to response in case of success case.
	// Status code set at ResponseEntity inside method
	// will override this valie
	@ResponseStatus(value = HttpStatus.OK) 
	public JsonResponse getAll() {
		Employee employee = new Employee();
		employee.setFirstName("Amit");
		employee.setLastName("Patil");
		employee.setId(UUID.randomUUID().toString());
		
		List<Employee> employees = Arrays.asList(employee);
		
		return new JsonResponse().setData(employees).setHttpStatus(HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{employeeId}/profilePicture", method = RequestMethod.POST)
	public String uploadProfilePicture(final @PathVariable(name = "employeeId") String employeeId,
			@RequestParam(name = "file") MultipartFile file) {

		if (file != null) {
			try {
				file.transferTo(new File(HOME_AMIPATIL_TEMO + file.getOriginalFilename()));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		final String path = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/")
				.path(file.getOriginalFilename()).build().toUriString();
		return path;
	}

	/**
	 * Dot [.] in path variable is parsed differently in spring. It removes
	 * everything after dot in path parameter considering it to be extension. So, if
	 * we have path parameter that is last in url string then we must add regex to
	 * support dot [.] in it. There are other better solutions for this [at config
	 * level], check. This is simplest but needs to be done at all places in source
	 * code. NOTE: This behavior is only in case of last path variable having dot in
	 * it.
	 * 
	 * @param fileName
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "{employeeId}/profilePicture/{fileName:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getProfilePicture(final @PathVariable(name = "fileName") String fileName,
			HttpServletRequest request) throws IOException {

		FileSystemResource fileSystemResource = new FileSystemResource(HOME_AMIPATIL_TEMO + fileName);
		String mimeType = Files.probeContentType(fileSystemResource.getFile().toPath());

		/**
		 * This is how we should define headers while file download API. We must set, 1.
		 * MediaType 2. ContentDisposition So, that client can know type of file and how
		 * to response to file download whether to open it [inline] or to download it
		 * and save [attacment]
		 */
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"inline; filename=\"" + fileSystemResource.getFilename() + "\"")
				.contentType(MediaType.parseMediaType(mimeType)).body(fileSystemResource); // This is how we should set
																							// body [binary data] in
																							// response.

	}
	
	@PostMapping(consumes =  {"application/xml", "application/json"})
	public JsonResponse create(@RequestBody Employee employee) {
		if (employee != null) {
			System.out.println("Body got parsed correctly, employee :: " + employee);
			employee.setId(UUID.randomUUID().toString());
		}
		return new JsonResponse().setHttpStatus(HttpStatus.CREATED).setMessage("Created").setData(employee);
	}
}
