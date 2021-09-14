package com.shahinkhalajestani.currencyexchangeservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api",fallbackMethod = "hardCodedResponse")
    @CircuitBreaker(name = "sample-api",fallbackMethod = "hardCodedResponse")
    public String sampleApi() {
        logger.info("sample api call recieved ");
        ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return response.getBody();
    }

    public String hardCodedResponse(Exception exception){
        return "fallback response for sample api ";
    }

}
