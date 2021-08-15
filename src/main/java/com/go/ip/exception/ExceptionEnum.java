package com.go.ip.exception;

import com.go.ip.util.IpConstants;

public enum ExceptionEnum {
	
	INVALID_CAPACITY_ERROR(IpConstants.INVALID_CAPACITY_ERROR_CODE, IpConstants.INVALID_CAPACITY_ERROR_SUMMARY),
	INVALID_IP_POOL_ERROR(IpConstants.INVALID_IP_POOL_ERROR_CODE, IpConstants.INVALID_IP_POOL_ERROR_SUMMARY),
	INTERNAL_SERVER_ERROR(IpConstants.INTERNAL_SERVER_ERROR_CODE, IpConstants.INTERNAL_SERVER_ERROR_SUMMARY);
	
	private String errorCode;
	private String errorSummary;
	
	/*
	 * private constructor to hide the default implementation
	 */
	private ExceptionEnum(String errorCode, String errorSummary) {
		this.errorCode = errorCode;
		this.errorSummary = errorSummary;
	}

	/*
	 * error code getter
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/*
	 * error summary getter
	 */
	public String getErrorSummary() {
		return errorSummary;
	}

}
