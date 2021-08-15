package com.go.ip.util;

public class IpConstants {
	
	public static final String GO = "/go";
	public static final String V1_0 = "1.0";
	public static final String IP_URI = "/ip";
	public static final String ADDRESS_URI = "/address";
	public static final String POOL_URI = "/pool";
	public static final String DOT_JOINER = ".";
	
	//Error Constants
	public static final String INTERNAL_SERVER_ERROR_CODE = "IP-ERROR-001";
	public static final String INTERNAL_SERVER_ERROR_SUMMARY = "Internal Server Error";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something went wrong. Please try again later.";
	public static final String INVALID_CAPACITY_ERROR_CODE = "IP-ERROR-002";
	public static final String INVALID_CAPACITY_ERROR_SUMMARY = "Invalid Capacity. Please try with a valid capacity";
	public static final String INVALID_IP_POOL_ERROR_CODE = "IP-ERROR-003";
	public static final String INVALID_IP_POOL_ERROR_SUMMARY = "Ip Pool Not Found. Please try with a valid Pool Id.";
	
	//Http Status Codes for Spring docs
	public static final String OK = "200";
	public static final String CREATED = "201";
	public static final String BAD_REQUEST = "400";
	public static final String NOT_IN_RANGE = "416";
	public static final String NOT_FOUND = "404";
	public static final String INTERNAL_SERVER_ERROR = "500";
	
	public static final String QUERY_POOL_ID = "poolId";
	
}
