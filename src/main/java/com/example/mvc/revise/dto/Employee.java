package com.example.mvc.revise.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2821654871402178482L;
	
	private String id;

	private String firstName;

	private String lastName;

	// we can use all Jackson/Jaxb annotations to define format of Date fields
	// in serialized Json/XML and vice versa
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dob = new Date();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
