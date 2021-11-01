package com.shahintraining.aggregatorservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.aggregatorservice.jwt.JwtUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    private final JwtUtility jwtUtility;

    public CustomAuthorizationFilter(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/aggregator-service/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (jwtUtility.checkAuthorizationHeader(authorizationHeader)) {
                try {
                    String username = jwtUtility.extractUsernameFromToken(authorizationHeader);
                    String[] roles = jwtUtility.extractRolesFromToken(authorizationHeader);
                    List<SimpleGrantedAuthority> grantedAuthorities = Arrays.stream(roles).
                            map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    Authentication authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                } catch (Exception e) {
                    Map<String,String> errors = new HashMap<>();
                    response.setHeader("error",e.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    errors.put("error_message",e.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),errors);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
