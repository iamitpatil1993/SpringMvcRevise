package com.example.mvc.revise.dto;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.hateoas.RepresentationModel;

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
// We can extend our Entities/DTOs with this class, which adds links to our model and useful APIs to add links
// But, I don't like to extent framework classes, because this locks down this class and I can't extent other classes if need comes.
// So, to support HATEOAs, I think using EntityModel and wrapping links and our Entities/DTOs in it seems to be better way. Which will save
// us from extending any classes like this RepresentationModel
public class CustomerGetDto extends RepresentationModel<CustomerGetDto> implements Serializable {

	private static final long serialVersionUID = 3076703131063099303L;

	private Long id;

	private String fistName;

	private String lastName;

	private Calendar createdOn;

	private Calendar updatedOn;
}
