package com.springredis.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private String description;

	private List<FieldError> fieldErrors = new ArrayList<>();

	public ErrorDTO(String message) {
		this.message = message;
	}

	public ErrorDTO(String code, String message) {
		this(code, message, null, null);
	}

	public ErrorDTO(String code, String message, String description) {
		this(code, message, description, null);
	}

	public ErrorDTO(String code, String message, String description, List<FieldError> fieldErrors) {
		this.code = code;
		this.message = message;
		this.description = description;
		this.fieldErrors = fieldErrors;
	}

	public void add(String objectName, String field, String message) {
		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
		}
		fieldErrors.add(new FieldError(objectName, field, message));
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
