package com.shahintraining.aggregatorservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.aggregatorservice.domain.LoginDto;
import com.shahintraining.aggregatorservice.domain.UsernamePasswordRequest;
import com.shahintraining.aggregatorservice.exception.UserNotFoundException;
import com.shahintraining.aggregatorservice.jwt.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
@RequiredArgsConstructor
@Service
@Transactional
public class JwtServiceImpl implements JwtService {

    private final JwtUtility jwtUtility;
    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> responseData = new HashMap<>();
        try {
            if (jwtUtility.checkAuthorizationHeader(authorizationHeader)) {
                String username = jwtUtility.extractUsernameFromToken(authorizationHeader);
                UserDetails userDetails = userService.loadUserByUsername(username);
                String accessToken = jwtUtility.generateAccessToken(userDetails, request.getRequestURI());
                String refreshToken = jwtUtility.generateRefreshToken(userDetails, request.getRequestURI());
                responseData.put("access_token", accessToken);
                responseData.put("refresh_token", refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), responseData);
            } else {
                responseData.put("error", "refresh Token is missing");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public LoginDto generateTokens(UsernamePasswordRequest usernamePasswordRequest) {
        UserDetails userDetails = userService.loadUserByUsername(usernamePasswordRequest.getUsername());
        if (!passwordEncoder.matches(usernamePasswordRequest.getPassword(), userDetails.getPassword())) {
            throw new UserNotFoundException("user credentials were not valid!");
        }
        String accessToken = jwtUtility.generateAccessToken(userDetails, "custom-user-login");
        String refreshToken = jwtUtility.generateRefreshToken(userDetails, "custom-user-login");
        return new LoginDto(refreshToken, accessToken);
    }
}
