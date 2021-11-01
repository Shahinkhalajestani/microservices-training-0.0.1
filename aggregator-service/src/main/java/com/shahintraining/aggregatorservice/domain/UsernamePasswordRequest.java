package com.shahintraining.aggregatorservice.domain;

import lombok.Data;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
@Data
public class UsernamePasswordRequest {
    private String username;
    private String password;
}
