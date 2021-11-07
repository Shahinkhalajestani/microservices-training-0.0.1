package com.shahinkhalajestani.apigateway.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistration;

import java.util.function.Function;

@Configuration
public class ApiGateWayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec.path("/**")
                        .uri("lb://aggregator-service")).build();
//                .route(predicateSpec -> predicateSpec.path("/aggregator-service/**")
//                        .uri("lb://aggregator-service"))
//                .route(predicateSpec -> predicateSpec.path("/swagger-ui/","/swagger-ui/**")
//                        .uri("lb://aggregator-service")).build();
        //idk what is the usage for this
//                .route(p -> p.path("/get")
//                        .filters(f -> f.addRequestHeader("MyHeader", "MyUri")
//                                .addRequestParameter("Param", "MyValue"))
//                        .uri("http://httpbin.org:80"))
//                .route(p -> p.path("/swagger-ui/**")
//                        .uri("lb://swagger-ui"))
//                .route(p -> p.path("/currency-exchange/**")
//                        .uri("lb://currency-exchange"))
//                .route(p -> p.path("/currency-conversion/**")
//                        .uri("lb://currency-conversion"))
//                .route(p -> p.path("/currency-conversion-feign/**")
//                        .uri("lb://currency-conversion"))
//                .route(p -> p.path("/currency-conversion-new/**")
//                        .filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
//                                "/currency-conversion-feign/${segment}"))
//                        .uri("lb://currency-conversion"))
//                .build();
    }
}
