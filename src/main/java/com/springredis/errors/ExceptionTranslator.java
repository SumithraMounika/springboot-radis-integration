package com.springredis.errors;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionTranslator {

	@ExceptionHandler(ConcurrencyFailureException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorDTO processConcurrencyError(ConcurrencyFailureException ex) {
		return new ErrorDTO(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorDTO processAccessDeniedException(AccessDeniedException ex) {
		ErrorDTO errorDTO = new ErrorDTO("400", "Access denied", ex.getMessage());
		return errorDTO;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ErrorDTO proccessMethodNotSupportedExcpetion(HttpRequestMethodNotSupportedException ex) {
		return new ErrorDTO(ex.getMessage());
	}
	
	@ExceptionHandler(MappingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO proccessNoMappingFound(MappingException ex) {
		return new ErrorDTO(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<ErrorDTO> processRuntimeException(Exception ex) throws Exception {
		ResponseEntity<ErrorDTO> responseEntity;
		ErrorDTO errorDTO;

		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		if (responseStatus != null) {
			errorDTO = new ErrorDTO("500", "error." + responseStatus.value().value(), responseStatus.reason());
			responseEntity = new ResponseEntity<>(errorDTO, responseStatus.value());
		} else {
			errorDTO = new ErrorDTO("500", "Internal server error", ex.getMessage());
			responseEntity = new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	public ErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
		ErrorDTO errorDTO = new ErrorDTO("Error Validation");
		for (FieldError fieldError : fieldErrors) {

			errorDTO.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
		}
		return errorDTO;
	}

}
