package com.shahintraining.aggregatorservice.proxy;

import feign.auth.BasicAuthRequestInterceptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/8/2021
 */
@Configuration
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "currency.conversion")
public class FeignClientConfiguration {

    private String username;
    private String password;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor(username,password);
    }

}
