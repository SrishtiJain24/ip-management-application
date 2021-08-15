package com.go.ip.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.go.ip.model.IpAddress;
import com.go.ip.model.IpGenerationRequest;
import com.go.ip.model.IpPool;
import com.go.ip.repository.IpAddressRepository;
import com.go.ip.repository.IpPoolRepository;
import com.go.ip.service.IpManagementService;
import com.go.ip.util.IpConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IpManagementServiceImpl implements IpManagementService {

	@Autowired
	IpPoolRepository ipPoolRepository;

	@Autowired
	IpAddressRepository ipAddressRepository;

	public IpPool getPoolDetails(Long poolId) {
		Optional<IpPool> ipPool = ipPoolRepository.findById(poolId);
		if (ipPool.isPresent()) {
			return ipPool.get();
		} else {
			return null;
		}
	}

	/**
	 * Assuming IP address will always have 4 . separated numbers. And numbers are
	 * assigned in incremental order of lower bound.
	 */
	public List<String> generateIpAddress(IpGenerationRequest ipRequest) {
		IpPool ipPool = getPoolDetails(ipRequest.getIpPoolId());
		if (ipRequest.getTotalIp() <= (ipPool.getTotalCapacity() - ipPool.getUsedCapacity())) {
			log.debug("Required capacity available in Pool. Proceeding with creating IPs");
			String ipAddressPrefix = ipPool.getLowerBound().substring(0,
					ipPool.getLowerBound().lastIndexOf(IpConstants.DOT_JOINER));
			List<IpAddress> ipAddressList = createIPAddresses(ipPool.getUsedCapacity(), ipRequest.getTotalIp(), ipPool,
					ipAddressPrefix);
			persistIPPoolChanges(ipPool, ipRequest.getTotalIp());
			return ipAddressList.stream().map(IpAddress::getValue).collect(Collectors.toList());
		}
		return null;
	}

	public List<IpAddress> createIPAddresses(Long ipStartIndex, Long totalIp, IpPool ipPool, String ipAddressPrefix) {
		log.debug("Generating {} Ip addresses from index : {} for Ip Pool: {}", totalIp, ipStartIndex, ipPool);
		List<IpAddress> ipAddressList = new ArrayList<>();
		for (int i = 0; i < totalIp; i++) {
			IpAddress ipAddress = new IpAddress();
			Long ipHost = ipStartIndex + i;
			String ipValue = getIpValue(ipAddressPrefix, String.valueOf(ipHost));
			ipAddress.setIpPoolId(ipPool);
			ipAddress.setValue(ipValue);
			ipAddressList.add(ipAddress);
		}
		log.debug("Ip Addresses generated: {}", ipAddressList);
		persistIPAddresses(ipAddressList);
		return ipAddressList;
	}

	public void persistIPAddresses(List<IpAddress> ipAddressList) {
		ipAddressRepository.saveAll(ipAddressList);
	}

	public void persistIPPoolChanges(IpPool ipPool, Long totalIp) {
		ipPool.setUsedCapacity(ipPool.getUsedCapacity() + totalIp);
		log.debug("Ip Pool Updated: {}", ipPool);
		ipPoolRepository.save(ipPool);
	}

	static String getIpValue(String prefix, String host) {
		return prefix.concat(IpConstants.DOT_JOINER).concat(host);
	}

}
