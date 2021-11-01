package com.shahintraining.aggregatorservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.aggregatorservice.domain.UsernamePasswordRequest;
import com.shahintraining.aggregatorservice.jwt.JwtUtility;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationManager authenticationManager1, JwtUtility jwtUtility) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager1;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UsernamePasswordRequest usernamePasswordRequest =
                    new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordRequest.class);

            Authentication authenticationToken =
                    new UsernamePasswordAuthenticationToken(usernamePasswordRequest.getUsername()
                            , usernamePasswordRequest.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
        String access_token = jwtUtility.generateAccessToken(user, request.getRequestURI());
        String refresh_Token= jwtUtility.generateRefreshToken(user,request.getRequestURI());
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_Token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
    }
}
