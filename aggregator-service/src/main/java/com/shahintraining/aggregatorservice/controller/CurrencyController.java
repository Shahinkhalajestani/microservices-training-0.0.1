package com.shahintraining.aggregatorservice.controller;

import com.shahintraining.aggregatorservice.domain.CurrencyConversion;
import com.shahintraining.aggregatorservice.proxy.CurrencyConversionProxy;
import com.shahintraining.aggregatorservice.proxy.CurrencyExchangeProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/3/2021
 */
@RestController
@RequiredArgsConstructor

public class CurrencyController {

    private final CurrencyConversionProxy currencyConversionProxy;
    private final CurrencyExchangeProxy currencyExchangeProxy;


    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping("/aggregator-service/currency/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversion> calculateCurrencyConversion(@PathVariable("from") String from,
                                                                          @PathVariable("to") String to,
                                                                          @PathVariable("quantity") BigDecimal quantity) {

        return new ResponseEntity(currencyConversionProxy.returnConvertedValue(from, to, quantity), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping("/aggregator-service/currency/from/{from}/to/{to}")
    public ResponseEntity<Object> calculateCurrencyExchange(@PathVariable("from") String from,
                                                                          @PathVariable("to") String to) {
        return new ResponseEntity(currencyExchangeProxy.returnExchangedValue(from, to), HttpStatus.OK);
    }

}
