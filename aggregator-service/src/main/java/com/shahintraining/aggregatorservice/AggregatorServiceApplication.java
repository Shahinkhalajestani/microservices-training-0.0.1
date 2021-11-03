package com.shahintraining.aggregatorservice;

import com.shahintraining.aggregatorservice.domain.AppUser;
import com.shahintraining.aggregatorservice.domain.Role;
import com.shahintraining.aggregatorservice.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
@EnableFeignClients
public class AggregatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregatorServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AppUserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));

            userService.saveUser(new AppUser(null,"shahin@123","123","shahin",new ArrayList<>()));
            userService.saveUser(new AppUser(null,"ramtin@123","123","ramtin",new ArrayList<>()));
            userService.saveUser(new AppUser(null,"ali@123","123","ali",new ArrayList<>()));

            userService.addRoleToUser("shahin@123","ROLE_SUPER_ADMIN");
            userService.addRoleToUser("shahin@123","ROLE_MANAGER");
            userService.addRoleToUser("shahin@123","ROLE_USER");
            userService.addRoleToUser("shahin@123","ROLE_ADMIN");
            userService.addRoleToUser("ramtin@123","ROLE_ADMIN");
            userService.addRoleToUser("ramtin@123","ROLE_MANAGER");
            userService.addRoleToUser("ramtin@123","ROLE_USER");
            userService.addRoleToUser("ali@123","ROLE_USER");
        };
    }

}
