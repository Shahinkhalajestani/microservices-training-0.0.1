package com.shahinkhalajestani.currencyexchangeservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {

    @GetMapping("/getSample")
    public String sampleApi(){
        return "Sample API";
    }

}
