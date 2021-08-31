package com.shahinkhalajestani.currencyexchangeservice.controllers;

import com.shahinkhalajestani.currencyexchangeservice.models.CurrencyExchange;
import com.shahinkhalajestani.currencyexchangeservice.service.CurrencyExchangeService;
import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping(value = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
                                                        @PathVariable String to) {
        List<CurrencyExchange> currencyExchangeList = currencyExchangeService.findByFromAndTo(from, to);
        if (currencyExchangeList==null || currencyExchangeList.isEmpty()){
            throw new RuntimeException("Currency List Is Empty");
        }
        CurrencyExchange currencyExchange = currencyExchangeList.get(0);
        return currencyExchange;
    }

    @GetMapping(value = "/currency-exchange/init")
    public List<CurrencyExchange> initializeData() {
        return currencyExchangeService.initializeFakeData();
    }

}
