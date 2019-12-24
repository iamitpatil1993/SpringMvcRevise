package com.example.mvc.revise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldValidationError {

	private String objectName;

	private String field;

	private String error;

}
