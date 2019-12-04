package com.example.mvc.revise.dto;

import java.io.Serializable;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * We should always consider using Separate DTOs for different HTTP operations
 * on resource Types. This is because, we can control what user can send in
 * POST, PUT using request pojo, and hence user can not set more than necessary
 * information in request body and hence unnecessary fields will not mapped to
 * internal domain object and hence will not reflect in database.
 * 
 * Having ID field in this post call body make no sense hence we should have
 * separate DTOs
 * 
 * @author amipatil
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPostDto implements Serializable {

	private static final long serialVersionUID = -7515583505289341488L;

	private String firstName;

	private String lastName;

	private Calendar createdOn = Calendar.getInstance();

	private Calendar updatedOn = Calendar.getInstance();
}
