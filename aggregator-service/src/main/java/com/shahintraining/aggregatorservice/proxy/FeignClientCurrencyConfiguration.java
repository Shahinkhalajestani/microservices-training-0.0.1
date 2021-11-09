package com.shahintraining.aggregatorservice.proxy;

import feign.auth.BasicAuthRequestInterceptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/8/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeignClientCurrencyConfiguration {

    @Value("${currency.conversion.username}")
    private String username;
    @Value("${currency.conversion.password}")
    private String password;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor(username,password);
    }

}
