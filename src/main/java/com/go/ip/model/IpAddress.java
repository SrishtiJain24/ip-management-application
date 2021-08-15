package com.go.ip.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ip_address")
public class IpAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ipAddressId;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ip_pool_id", referencedColumnName = "ip_pool_id")
	private IpPool ipPoolId;
	
	private String value;

}
