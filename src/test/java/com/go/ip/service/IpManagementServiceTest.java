package com.go.ip.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.go.ip.model.IpGenerationRequest;
import com.go.ip.model.IpPool;
import com.go.ip.repository.IpAddressRepository;
import com.go.ip.repository.IpPoolRepository;
import com.go.ip.service.impl.IpManagementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class IpManagementServiceTest {

	@Mock
	private IpPoolRepository ipPoolRepository;

	@Mock
	private IpAddressRepository ipAddressRepository;

	@Autowired
	@InjectMocks
	private IpManagementServiceImpl ipManagementService;

	private IpPool ipPool1;
	private IpPool ipPool2;
	List<IpPool> ipPoolList;

	IpGenerationRequest ipGenRequest;
	List<String> ipGenResponse;

	@BeforeEach
	public void setUp() {
		ipPoolList = new ArrayList<>();
		ipPool1 = new IpPool(1L, "Test Pool - 0", 256L, 0L, "10.2.3.0", "10.2.3.255");
		ipPool2 = new IpPool(2L, "Test Pool - 1", 256L, 0L, "10.2.4.0", "10.2.4.255");
		ipPoolList.add(ipPool1);
		ipPoolList.add(ipPool2);
		ipGenRequest = new IpGenerationRequest(2L, 1L);
		ipGenResponse = new ArrayList<>();
		ipGenResponse.add("10.2.3.0");
		ipGenResponse.add("10.2.3.1");
	}

	@AfterEach
	public void tearDown() {
		ipPool1 = ipPool2 = null;
		ipPoolList = null;
		ipGenRequest = null;
		ipGenResponse = null;
	}

	@Test
	public void givenIdThenShouldReturnIpPoolOfThatId() {
		Mockito.when(ipPoolRepository.findById(1L)).thenReturn(Optional.ofNullable(ipPool1));
		assertThat(ipManagementService.getPoolDetails(ipPool1.getIpPoolId())).isEqualTo(ipPool1);
	}

	@Test
	public void givenIpRequestThenShouldGenerateIpAddressOfThatPool() {
		Mockito.when(ipPoolRepository.findById(1L)).thenReturn(Optional.ofNullable(ipPool1));
		assertThat(ipManagementService.generateIpAddress(ipGenRequest)).isEqualTo(ipGenResponse);
	}

}
