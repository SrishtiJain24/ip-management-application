package com.go.ip.model;

import lombok.Data;

@Data
public class IpPool {
	
	private String id;
	private String description;
	private Long totalCapacity;
	private Long usedCapacity;
	private String lowerBound;
	private String uppperBound;

}
