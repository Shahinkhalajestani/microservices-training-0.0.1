package com.shahinkhalajestani.currencyconversionservice.proxy;

import com.shahinkhalajestani.currencyconversionservice.models.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange")
public interface CurrencyControllerProxy {

    @GetMapping(value = "/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from,
                                             @PathVariable String to);
}
