package com.shahintraining.aggregatorservice.jwt;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
@Configuration
@PropertySource(value = "classpath:security.properties", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "application.jwt")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtConfig {

    private  String tokenPrefix;
    private  String secureKey;
    private  Integer accessTokenExpirationAfterDays;
    private  Integer accessTokenExpirationAfterMinutes;
    private  Integer refreshTokenExpirationAfterDays;

}
