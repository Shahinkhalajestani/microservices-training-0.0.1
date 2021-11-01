package com.shahintraining.aggregatorservice.controller;

import com.shahintraining.aggregatorservice.domain.AppUser;
import com.shahintraining.aggregatorservice.domain.Role;
import com.shahintraining.aggregatorservice.domain.RoleToUserDto;
import com.shahintraining.aggregatorservice.service.AppUserService;
import com.shahintraining.aggregatorservice.service.JwtTokenRefreshService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/19/2021
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/aggregator-service")
public class UserController {

    private final AppUserService userService;
    private final JwtTokenRefreshService jwtTokenService;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping(value = "/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping(value = "/user/save")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping(value = "/role/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping(value = "/role/addRoleToUser")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserDto roleToUserDto) {
        userService.addRoleToUser(roleToUserDto.getUsername(), roleToUserDto.getRoleTitle());
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        jwtTokenService.refreshToken(request, response);
    }


}
