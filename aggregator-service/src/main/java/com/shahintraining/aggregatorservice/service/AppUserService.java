package com.shahintraining.aggregatorservice.service;

import com.shahintraining.aggregatorservice.domain.AppUser;
import com.shahintraining.aggregatorservice.domain.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
public interface AppUserService extends UserDetailsService {
    Role saveRole(Role role);
    List<AppUser> getUsers();
    AppUser saveUser(AppUser user);
    void deleteUser(Long userId);
    AppUser getUser(Long userId);
    void addRoleToUser(String username,String roleTitle);
    AppUser getUser(String username);
}
