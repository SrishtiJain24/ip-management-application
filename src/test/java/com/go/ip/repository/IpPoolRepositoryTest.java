package com.go.ip.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.go.ip.model.IpPool;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class IpPoolRepositoryTest {
	
	@Autowired
    private IpPoolRepository ipPoolRepository;
	IpPool ipPool;
	
	@BeforeEach
    void setUp() {
        ipPool = new IpPool(1L, "Test IP Pool", 256L, 0L, "10.2.3.0", "10.2.3.255");   
    }

    @AfterEach
    public void destroyAll(){
    	ipPoolRepository.deleteAll();
    }

    @Test
    void testSaveIpPool() { 
    	IpPool savedPool=ipPoolRepository.save(ipPool);
        assertThat(savedPool.getIpPoolId()).isEqualTo(ipPool.getIpPoolId());
    }
    
    @Test
    void testGetPoolDetails() { 
    	ipPoolRepository.save(ipPool);
    	 Optional<IpPool> fetchedPool =  ipPoolRepository.findById(ipPool.getIpPoolId());
        assertThat(fetchedPool.get().getIpPoolId()).isEqualTo(ipPool.getIpPoolId());
    }

}
