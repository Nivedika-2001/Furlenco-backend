package com.flurenco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ErrorStatus> userNotFoundExceptionHandler(UserNotFound userNotFoundException){
		ErrorStatus errorStatus= new ErrorStatus();
		errorStatus.setDetails(userNotFoundException.toString());
		errorStatus.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStatus.setMessage(userNotFoundException.getMessage());	
		return new ResponseEntity<ErrorStatus>(errorStatus,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ProductNotFound.class)
	public ResponseEntity<ErrorStatus> productNotFoundExceptionHandler(ProductNotFound productNotFoundException){
		ErrorStatus errorStatus= new ErrorStatus();
		errorStatus.setDetails(productNotFoundException.toString());
		errorStatus.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStatus.setMessage(productNotFoundException.getMessage());	
		return new ResponseEntity<ErrorStatus>(errorStatus,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoSuchItemInCartExists.class)
	public ResponseEntity<ErrorStatus> noSuchItemInCartExistsExceptionHandler(NoSuchItemInCartExists noSuchItemInCartExists){
		ErrorStatus errorStatus= new ErrorStatus();
		errorStatus.setDetails(noSuchItemInCartExists.toString());
		errorStatus.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStatus.setMessage(noSuchItemInCartExists.getMessage());	
		return new ResponseEntity<ErrorStatus>(errorStatus,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<ErrorStatus> duplicateRecordExceptionHandler(DuplicateRecordException duplicateRecordException){
		ErrorStatus errorStatus= new ErrorStatus();
		errorStatus.setDetails(duplicateRecordException.toString());
		errorStatus.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStatus.setMessage(duplicateRecordException.getMessage());	
		return new ResponseEntity<ErrorStatus>(errorStatus,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserRoleNotFoundException.class)
	public ResponseEntity<ErrorStatus> userRoleNotFoundExceptionHandler(UserRoleNotFoundException userRoleNotFoundException){
		ErrorStatus errorStatus= new ErrorStatus();
		errorStatus.setDetails(userRoleNotFoundException.toString());
		errorStatus.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStatus.setMessage(userRoleNotFoundException.getMessage());	
		return new ResponseEntity<ErrorStatus>(errorStatus,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ProductDeletionException.class)
	public ResponseEntity<ErrorStatus> productDeletionExceptionHandler(ProductDeletionException productDeletionException){
		ErrorStatus errorStatus= new ErrorStatus();
		errorStatus.setDetails(productDeletionException.toString());
		errorStatus.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStatus.setMessage(productDeletionException.getMessage());	
		return new ResponseEntity<ErrorStatus>(errorStatus,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorStatus> exceptionHandler(Exception exception){
		ErrorStatus errorStatus= new ErrorStatus();
		errorStatus.setDetails(exception.toString());
		errorStatus.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStatus.setMessage(exception.getMessage());	
		return new ResponseEntity<ErrorStatus>(errorStatus,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}



	


