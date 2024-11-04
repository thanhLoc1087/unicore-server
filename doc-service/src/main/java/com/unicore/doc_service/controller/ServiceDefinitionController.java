package com.unicore.doc_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.doc_service.config.swagger.ServiceDefinitionContext;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author satish sharma
 * <pre>
 *  Controller to serve the JSON from our in-memory store. So that UI can render the API-Documentation	
 * </pre>
 */
@RestController
@RequiredArgsConstructor
public class ServiceDefinitionController {
	private final ServiceDefinitionContext definitionContext;
	
	@GetMapping("/service/{servicename}")
	public String getServiceDefinition(@PathVariable("servicename") String serviceName){
		
		return definitionContext.getSwaggerDefinition(serviceName);
		
	}
}