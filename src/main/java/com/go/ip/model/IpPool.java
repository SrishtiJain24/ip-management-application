package com.go.ip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ip_pool")
public class IpPool {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ip_pool_id")
	private Long ipPoolId;
	
	private String description;
	private Long totalCapacity;
	private Long usedCapacity;
	private String lowerBound;
	private String upperBound;

}
