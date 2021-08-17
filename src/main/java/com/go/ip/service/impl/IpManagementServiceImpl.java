package com.go.ip.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.go.ip.exception.GenericException;
import com.go.ip.exception.InvalidCapacityException;
import com.go.ip.exception.IpPoolNotFoundException;
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
	
	
	/**
	 * Fetches pool Id from DB. Returns error if not found
	 * 
	 * @param poolId
	 * @return IpPool
	 * @throws IpPoolNotFoundException, GenericException
	 */
	public IpPool getPoolDetails(Long poolId) {
		Optional<IpPool> ipPool;
		try {
			ipPool = ipPoolRepository.findById(poolId);
		} catch (Exception ex) {
			log.error("Exception Occured while fetching Ip Pool Details: {}", ex);
			throw new GenericException(ex.getMessage());
		}
		if (ipPool.isPresent()) {
			return ipPool.get();
		} else {
			throw new IpPoolNotFoundException("Ip Pool does not exist.");
		}
	}


	/**
	 * 
	 * Validates if pool id exists and required capacity is available, else throw error.
	 * Creates the required IPs in DB
	 * Updates the used capacity of the IP pool
	 * 
	 * Assuming numbers are assigned in incremental order of lower bound.
	 * 
	 * @param IpGenerationRequest
	 * @return  List of Ip addresses
	 * @throws IpPoolNotFoundException,InvalidCapacityException,GenericException
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
		} else {
			throw new InvalidCapacityException("Required capacity not available");
		}
	}

	/**
	 * 
	 * Creates the required IP addresses
	 * 
	 * Assuming numbers are assigned in incremental order of lower bound.
	 * 
	 * @param ipStartIndex, required Ips, IpPool, ipAddressPrefix
	 * @return  List of Ip addresses
	 */
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


	/**
	 * 
	 * Persist all Ip addresses in DB. returns error if it fails
	 * 
	 * @param List of Ip addresses
	 * @throws GenericException
	 */
	public void persistIPAddresses(List<IpAddress> ipAddressList) {
		try {
			ipAddressRepository.saveAll(ipAddressList);
		} catch (Exception ex) {
			log.error("Exception Occured while saving Ip Addresses: {}", ex);
			throw new GenericException(ex.getMessage());
		}
	}


	/**
	 * 
	 * Update Ip pool details in DB. returns error if it fails
	 * 
	 * @param IpPool, used Capacity
	 * @throws GenericException
	 */
	public void persistIPPoolChanges(IpPool ipPool, Long totalIp) {
		try {
			ipPool.setUsedCapacity(ipPool.getUsedCapacity() + totalIp);
			log.debug("Ip Pool Updated: {}", ipPool);
			ipPoolRepository.save(ipPool);
		} catch (Exception ex) {
			log.error("Exception Occured while saving Ip Pool: {}", ex);
			throw new GenericException(ex.getMessage());
		}
	}

	static String getIpValue(String prefix, String host) {
		return prefix.concat(IpConstants.DOT_JOINER).concat(host);
	}

}
