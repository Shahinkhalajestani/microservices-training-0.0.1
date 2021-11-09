package com.shahintraining.aggregatorservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/2/2021
 */
@FeignClient(name = "currency-exchange",configuration = FeignClientExchangeConfiguration.class)
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    Object returnExchangedValue(@PathVariable(value = "from") String from,
                                @PathVariable(value = "to") String to);

}
