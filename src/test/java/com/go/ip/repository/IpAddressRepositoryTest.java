package com.go.ip.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.go.ip.model.IpAddress;
import com.go.ip.model.IpPool;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class IpAddressRepositoryTest {
	
	@Autowired
    private IpAddressRepository ipAddressRepository;
	IpAddress ipAddress;
	IpPool ipPool;
	
	@BeforeEach
    void setUp() {
		ipPool = new IpPool(1L, "Test IP Pool", 256L, 0L, "10.2.3.0", "10.2.3.255");   
		ipAddress = new IpAddress(1L, ipPool,"10.2.3.0");   
    }

    @AfterEach
    public void destroyAll(){
    	ipAddressRepository.deleteAll();
    }
    
    @Test
    void testSaveIpAddress() { 
    	IpAddress savedAddress=ipAddressRepository.save(ipAddress);
        assertThat(savedAddress.getIpAddressId()).isEqualTo(ipAddress.getIpAddressId());
    }


}
