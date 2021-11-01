package com.shahintraining.aggregatorservice.service;

import com.shahintraining.aggregatorservice.domain.AppUser;
import com.shahintraining.aggregatorservice.domain.Role;
import com.shahintraining.aggregatorservice.repo.AppUserRepository;
import com.shahintraining.aggregatorservice.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {


    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public AppUser getUser(Long userId) {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        return checkEntityOptional(userOptional);
    }

    @Override
    public void addRoleToUser(String username, String roleTitle) {
        AppUser appUser = checkEntityOptional(userRepository.findAppUserByUsername(username));
        Role role = checkEntityOptional(roleRepository.findByTitle(roleTitle));
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        Optional<AppUser> userOptional = userRepository.findAppUserByUsername(username);
        return checkEntityOptional(userOptional);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = userRepository.findAppUserByUsername(username);
        AppUser user = checkEntityOptional(userOptional);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getTitle())).collect(Collectors.toList()));
    }

    private <T> T checkEntityOptional(Optional<T> optional) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
/*            if (optional.get() instanceof AppUser) {
                throw new UsernameNotFoundException("user is not present in database");
            }else if (optional.get() instanceof Role){
                throw new RuntimeException("not found");
            }
        }*/
            throw new RuntimeException("entity not found");
        }
    }

}
