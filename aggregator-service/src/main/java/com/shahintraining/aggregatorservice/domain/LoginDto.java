package com.shahintraining.aggregatorservice.domain;

import lombok.Data;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 1/10/2022
 */
@Data
public class LoginDto {
    public final String refreshToken;
    public final String accessToken;
}
