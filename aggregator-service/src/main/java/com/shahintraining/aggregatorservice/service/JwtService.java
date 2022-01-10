package com.shahintraining.aggregatorservice.service;

import com.shahintraining.aggregatorservice.domain.LoginDto;
import com.shahintraining.aggregatorservice.domain.UsernamePasswordRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
public interface JwtService {
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
    LoginDto generateTokens(UsernamePasswordRequest usernamePasswordRequest);
}
