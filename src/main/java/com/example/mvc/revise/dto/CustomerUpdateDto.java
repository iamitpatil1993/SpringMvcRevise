package com.example.mvc.revise.dto;

import java.io.Serializable;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDto implements Serializable {

	private static final long serialVersionUID = 1858847233763576967L;

	/**
	 * having ID in PUT call makes sense instead of POST, hence different DTOs helps
	 * us to maintain integrity of application
	 */
	private Long id;

	private String firstName;

	private String lastName;

	private Calendar updatedOn = Calendar.getInstance();
}
