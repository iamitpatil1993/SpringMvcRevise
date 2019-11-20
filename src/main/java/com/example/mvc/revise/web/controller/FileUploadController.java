package com.example.mvc.revise.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "fileUpload")
public class FileUploadController {

	/**
	 * This is how we can support multiple file uploads, just declare MultipartFile
	 * argument as a Array or Collection
	 * 
	 * We can use {@link javax.servlet.http.Part} here over {@link MultipartFile}
	 * 
	 * @param files
	 * @param model
	 * @return
	 */
	@PostMapping
	public String handleFileUpload(@RequestParam("file") List<MultipartFile> files, Model model) {

		files.stream().forEach(file -> {
			try {
				System.out.println("\n\n------------------------------------------------");
				System.out.println("file.getContentType() :: " + file.getContentType());
				System.out.println("file.getName() :: " + file.getName());
				System.out.println("file.getOriginalFilename() :: " + file.getOriginalFilename());
				System.out.println("file.getSize() :: " + file.getSize());
				model.addAttribute("message", "File uploaded successfully");
			} catch (Exception e) {
				model.addAttribute("message", "Error while uploading file");
			}

		});
		return "uploadResult";

	}

	/**
	 * THis is how we can handle file size limit exceeded Exception. Note: Exception
	 * is spring specific and not J2EE or Apache Commons file upload. So, this will
	 * support both
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = MultipartException.class)
	public String fileSizeExceededExceptionHandler(MultipartException exception) {
		System.out.println("Cause of error was :: " + exception.getMessage());
		return "uploadResult";
	}
}
