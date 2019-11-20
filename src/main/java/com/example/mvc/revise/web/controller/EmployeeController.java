package com.example.mvc.revise.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.mvc.revise.dto.Employee;

@RestController
@RequestMapping(path = "/api/employees")
public class EmployeeController {

	private static final String HOME_AMIPATIL_TEMO = "/home/amipatil/temp/";

	@GetMapping
	public Employee get() {
		Employee employee = new Employee();
		employee.setFirstName("Amit");
		employee.setLastName("Patil");

		return employee;
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
		 * This is how we should define headers while file download API. We must set,
		 * 1. MediaType
		 * 2. ContentDisposition
		 * So, that client can know type of file and how to response to file download whether to 
		 * open it [inline] or to download it and save [attacment]
		 */
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"inline; filename=\"" + fileSystemResource.getFilename() + "\"")
				.contentType(MediaType.parseMediaType(mimeType))
				.body(fileSystemResource); // This is how we should set body [binary data] in response.

	}
}
