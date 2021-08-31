package com.shahinkhalajestani.currencyexchangeservice.service;

import com.shahinkhalajestani.currencyexchangeservice.models.CurrencyExchange;
import com.shahinkhalajestani.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyExchangeService {

    private CurrencyExchangeRepository currencyExchangeRepository;

    private Environment environment;

    @Autowired
    public CurrencyExchangeService(CurrencyExchangeRepository currencyExchangeRepository,
                                   Environment environment) {

        this.currencyExchangeRepository = currencyExchangeRepository;
        this.environment=environment;
    }

    public List<CurrencyExchange> initializeFakeData(){
        String port = environment.getProperty("local.server.port");
        List<CurrencyExchange> exchangeList = new ArrayList<>();
        exchangeList.add(CurrencyExchange.builder().from("USD").to("INR").environment(port)
                .conversionMultiple(BigDecimal.valueOf(80)).build());
        exchangeList.add(CurrencyExchange.builder().from("USD").to("RR").environment(port)
                .conversionMultiple(BigDecimal.valueOf(30)).build());
        exchangeList.add(CurrencyExchange.builder().from("USD").to("SR").environment(port)
                .conversionMultiple(BigDecimal.valueOf(27)).build());
       return currencyExchangeRepository.saveAll(exchangeList);
    }
    public List<CurrencyExchange> findByFromAndTo(String from, String to){
        return currencyExchangeRepository.findAllByFromAndTo(from,to);
    }

}
