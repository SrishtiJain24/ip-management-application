package com.go.ip.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.go.ip.model.IpGenerationRequest;
import com.go.ip.model.IpPool;
import com.go.ip.util.IpConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = IpConstants.GO + "${server.apiContext}"
		+ "${server.versionContext}" + IpConstants.V1_0 + IpConstants.IP_URI)
public class IpManagementController {
	
	
	@PostMapping(value=IpConstants.ADDRESS_URI)
	public List<String> generateIpAddress(@RequestBody IpGenerationRequest ipRequest) {
		log.info("Request received to generate Ip Address: {}", ipRequest);

		return null;
	}
	
	@GetMapping(value=IpConstants.POOL_URI)
	public IpPool getIpPool(@RequestParam String poolId) {
		log.info("Request received to get Ip Pool: {}", poolId);

		return null;
	}
	
}