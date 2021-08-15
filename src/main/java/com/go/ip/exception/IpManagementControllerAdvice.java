package com.go.ip.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.go.ip.model.ErrorInfo;
import com.go.ip.util.IpConstants;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class IpManagementControllerAdvice {
	
	@ExceptionHandler(IpPoolNotFoundException.class)
	public ResponseEntity<ErrorInfo> handleException(IpPoolNotFoundException ex) {
		log.error("Exception occured in IP Management Application", ex);
		ErrorInfo apiError = new ErrorInfo(ExceptionEnum.INVALID_IP_POOL_ERROR,  ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidCapacityException.class)
	public ResponseEntity<ErrorInfo> handleException(InvalidCapacityException ex) {
		log.error("Exception occured - Required Capacity not available in Pool", ex);
		ErrorInfo apiError = new ErrorInfo(ExceptionEnum.INVALID_CAPACITY_ERROR,  ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
	}
	
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ErrorInfo> handleException(GenericException ex) {
		log.error("Exception occured in IP Management Application", ex);
		ErrorInfo apiError = new ErrorInfo(ExceptionEnum.INTERNAL_SERVER_ERROR, IpConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> handleException(Exception ex) {
		log.error("Exception occured in IP Management Application", ex);
		ErrorInfo apiError = new ErrorInfo(ExceptionEnum.INTERNAL_SERVER_ERROR, IpConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}	

}
