package com.go.ip.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class IpGenerationRequest {
	
	private Long totalIp;
	private Long ipPoolId;
}
