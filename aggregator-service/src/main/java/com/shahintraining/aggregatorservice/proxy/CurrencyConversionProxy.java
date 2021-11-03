package com.shahintraining.aggregatorservice.proxy;

import com.shahintraining.aggregatorservice.domain.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/2/2021
 */
@FeignClient(name = "currency-conversion")
public interface CurrencyConversionProxy {

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
     CurrencyConversion returnConvertedValue(@PathVariable(value = "from") String from,
                                                   @PathVariable(value = "to") String to,
                                                   @PathVariable(value = "quantity") BigDecimal quantity);

}
