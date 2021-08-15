package com.go.ip.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.go.ip.model.IpGenerationRequest;
import com.go.ip.model.IpPool;
import com.go.ip.service.IpManagementService;

@SpringBootTest
@AutoConfigureMockMvc
public class IpManagementControllerTests {

	@Mock
	private IpManagementService ipManagementService;

	@InjectMocks
	private IpManagementController ipManagementController;

	@Autowired
	private MockMvc mockMvc;
	
	private IpPool ipPool;
	private IpGenerationRequest ipGenerationRequest;
	List<String> ipGenerationResponse;

	@BeforeEach
	public void setup() {
		ipPool = new IpPool(1L, "Test Pool - 0", 256L, 0L, "10.2.3.0", "10.2.3.255");
		ipGenerationRequest = new IpGenerationRequest(2L,1L);
		ipGenerationResponse = new ArrayList<>();
		ipGenerationResponse.add("10.2.3.0");
		ipGenerationResponse.add("10.2.3.1");
	}

	@AfterEach
	void tearDown() {
		ipPool = null;
	}

	@Test
	public void testGetIpPoolDetails() throws Exception {
		when(ipManagementService.getPoolDetails(1L)).thenReturn(ipPool);
		mockMvc.perform(MockMvcRequestBuilders.get("/go/api/v1.0/ip/pool")
				.param("poolId", "1"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
				.getResponse()
				.getContentAsString()
				.contains("Test Pool - 0");
	}
	
	@Test
	public void testGenerateIpAddress() throws Exception {
		when(ipManagementService.generateIpAddress(ipGenerationRequest)).thenReturn(ipGenerationResponse);
		mockMvc.perform(MockMvcRequestBuilders.post("/go/api/v1.0/ip/address")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(ipGenerationRequest)))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
				.getResponse()
				.getContentAsString()
				.contains("10.2.3.0");
	}
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
