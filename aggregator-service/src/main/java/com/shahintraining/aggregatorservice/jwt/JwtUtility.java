package com.shahintraining.aggregatorservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shahintraining.aggregatorservice.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
@Component
@RequiredArgsConstructor
public class JwtUtility {

    private final JwtConfig jwtConfig;
    private final AppUserService userService;

    public String generateAccessToken(UserDetails user, String requestUri) {
       return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles",user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .withIssuer(requestUri)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(java.sql.Date
                        .valueOf(LocalDate.now().plusDays(jwtConfig.getAccessTokenExpirationAfterDays())))
                .sign(getAlgorithm());
    }

    public String generateRefreshToken(UserDetails user, String requestUri) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(requestUri)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(java.sql.Date
                        .valueOf(LocalDate.now().plusDays(jwtConfig.getRefreshTokenExpirationAfterDays())))
                .sign(getAlgorithm());
    }

    public String extractUsernameFromToken(String token){
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public String[] extractRolesFromToken(String token){
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaim("roles").asArray(String.class);
    }

    private DecodedJWT verifyToken(String token) {
        String tokenWithoutPrefix = token.substring(jwtConfig.getTokenPrefix().length());
        return JWT.require(getAlgorithm()).build()
                .verify(tokenWithoutPrefix);
    }

    private Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecureKey().getBytes());
        return algorithm;
    }

    public boolean checkAuthorizationHeader(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(jwtConfig.getTokenPrefix());
    }
}
