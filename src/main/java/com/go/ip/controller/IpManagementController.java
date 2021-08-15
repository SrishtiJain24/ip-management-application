package com.go.ip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.go.ip.model.ErrorInfo;
import com.go.ip.model.IpGenerationRequest;
import com.go.ip.model.IpPool;
import com.go.ip.service.IpManagementService;
import com.go.ip.util.IpConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = IpConstants.GO + "${server.apiContext}"
		+ "${server.versionContext}" + IpConstants.V1_0 + IpConstants.IP_URI)
public class IpManagementController {
	
	@Autowired
	IpManagementService ipManagementService;
	
	
	@Operation(summary = "Generate IP addresses for given pool id and capacity.", description = "Generate IP addresses for given pool id and specified capacity")
	@ApiResponses(value = {
			@ApiResponse(responseCode = IpConstants.CREATED, 
					description = " IP addresses generated for user", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = List.class))}
					),
			@ApiResponse(responseCode = IpConstants.NOT_FOUND, 
					description = "Input pool id does not exist", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorInfo.class))}
			),
			@ApiResponse(responseCode = IpConstants.NOT_IN_RANGE, 
					description = "Input capacity for IP generation is not available", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorInfo.class))}
			),
			@ApiResponse(responseCode = IpConstants.INTERNAL_SERVER_ERROR, 
					description = "Server error while generating IP addresses.", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorInfo.class))}
			)
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value=IpConstants.ADDRESS_URI, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<String> generateIpAddress(@RequestBody IpGenerationRequest ipRequest) {
		log.info("Request received to generate Ip Address: {}", ipRequest);
		return ipManagementService.generateIpAddress(ipRequest);
	}
	
	
	@Operation(summary = "Get IP Pool details for given pool id.", description = "Get IP Pool details for given pool id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = IpConstants.OK, 
					description = "IP Pool details returned to user", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = IpPool.class))}
					),
			@ApiResponse(responseCode = IpConstants.NOT_FOUND, 
					description = "Input pool id does not exist", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorInfo.class))}
			),
			@ApiResponse(responseCode = IpConstants.INTERNAL_SERVER_ERROR, 
					description = "Server error while getting IP Pool Details.", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorInfo.class))}
			)
	})
	@Parameters({
		@Parameter(name = IpConstants.QUERY_POOL_ID, description="IP Pool Identfier", required = true, schema = @Schema(implementation = Long.class), in = ParameterIn.QUERY)
	})
	@GetMapping(value=IpConstants.POOL_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public IpPool getIpPool(@RequestParam Long poolId) {
		log.info("Request received to get Ip Pool: {}", poolId);
		return ipManagementService.getPoolDetails(poolId);
	}
	
}