package com.go.ip.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.go.ip.model.IpPool;
import com.go.ip.repository.IpPoolRepository;
import com.go.ip.service.IpManagementService;

@Service
public class IpManagementServiceImpl implements IpManagementService {
	
	@Autowired
	IpPoolRepository ipPoolRepository;
	
	public IpPool getPoolDetails(Long poolId) {
		Optional<IpPool> ipPool = ipPoolRepository.findById(poolId);
		if(ipPool.isPresent()) {
			return ipPool.get();
		} else {
			return null;
		}
	}

}
