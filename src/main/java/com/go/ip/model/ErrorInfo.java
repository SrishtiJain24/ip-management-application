package com.go.ip.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.go.ip.exception.ExceptionEnum;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfo {
	
	private String errorCode;
    private String errorSummary;
    private String errorMessage;
    
    public ErrorInfo(ExceptionEnum exceptionEnum, String errorMessage) {
		super();
		this.errorCode = exceptionEnum.getErrorCode();
		this.errorSummary = exceptionEnum.getErrorSummary();
		this.errorMessage = errorMessage;
	}

}
