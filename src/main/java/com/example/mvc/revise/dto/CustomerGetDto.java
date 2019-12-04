package com.example.mvc.revise.dto;

import java.io.Serializable;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * We should always consider using Separate DTOs for different HTTP operations
 * on resource Types.
 * 
 * Get model should always expose fields which are relevant to client and should
 * not expose more than required domain details.
 * 
 * Mostly client uses get response DTO as a update pojo.
 * 
 * Fields like isDeleted, isactive make no sense to user
 * 
 * @author amipatil
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerGetDto implements Serializable {

	private static final long serialVersionUID = 3076703131063099303L;

	private Long id;

	private String fistName;

	private String lastName;

	private Calendar createdOn;

	private Calendar updatedOn;
}
