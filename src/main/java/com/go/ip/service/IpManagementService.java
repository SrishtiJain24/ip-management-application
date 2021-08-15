package com.go.ip.service;

import java.util.List;

import com.go.ip.model.IpGenerationRequest;
import com.go.ip.model.IpPool;

public interface IpManagementService {
	
	public IpPool getPoolDetails(Long poolId);
	
	public List<String> generateIpAddress(IpGenerationRequest ipRequest);

}
