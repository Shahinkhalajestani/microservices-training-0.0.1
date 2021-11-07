package com.shahintraining.aggregatorservice.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/6/2021
 */
@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiinfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT","Authorization","header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("golbal","accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0]=authorizationScope;
        return Arrays.asList(new SecurityReference("JWT",scopes));
    }

    private ApiInfo apiinfo() {
        return new ApiInfo("Microservices Api",
                "shahin's demo project for springcloud",
                "0.0.1"," ",new Contact("shahin khalajestani",
                "shahin.khalajestnai@yahoo.com","shahin.khalajestnai@yahoo.com"),
                "all rights belongs ro shahin khalajestani",
                " ",
                Collections.emptyList());
    }

}
