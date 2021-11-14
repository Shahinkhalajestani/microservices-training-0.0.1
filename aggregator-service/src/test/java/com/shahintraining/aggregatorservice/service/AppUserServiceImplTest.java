package com.shahintraining.aggregatorservice.service;

import com.shahintraining.aggregatorservice.domain.AppUser;
import com.shahintraining.aggregatorservice.domain.Role;
import com.shahintraining.aggregatorservice.repo.AppUserRepository;
import com.shahintraining.aggregatorservice.repo.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/13/2021
 */
@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AppUserService underTest;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder(10);
        underTest = new AppUserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void saveRole() {
        Role role = new Role(null, "SUPER_ADMIN");
        roleRepository.save(role);
        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository, atMost(1)).save(captor.capture());
        assertThat(role).isEqualTo(captor.getValue());
    }

    @Test
    void getUsers() {
        underTest.getUsers();
        verify(userRepository).findAll();
    }

    @Test
    void saveUser() {
        AppUser user = new AppUser(null, "shahin@123", "123123", "shahin", new ArrayList<>());
        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        underTest.saveUser(user);
        verify(userRepository).save(captor.capture());
        AppUser value = captor.getValue();
        assertThat(user).isEqualTo(value);
    }

    @Test
    void deleteUser() {
        Long id = 2L;
        underTest.deleteUser(id);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).deleteById(captor.capture());
        assertThat(id).isEqualTo(captor.getValue());
    }

    @Test
    void getUser() {
        Long id = 2L;
        AppUser user = new AppUser(null, "shahin@123", "123123", "shahin", new ArrayList<>());
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        underTest.getUser(id);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findById(captor.capture());
        assertThat(id).isEqualTo(captor.getValue());
    }

    @Test
    void addRoleToUser() {
        AppUser user = new AppUser(null, "shahin@123", "123123", "shahin", new ArrayList<>());
        Role role = new Role(null, "SUPER_ADMIN");
        String username = user.getUsername();
        String roleTitle = role.getTitle();
        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        when(userRepository.findAppUserByUsername(username)).thenReturn(Optional.of(user));
        when(roleRepository.findByTitle(roleTitle)).thenReturn(Optional.of(role));
        underTest.addRoleToUser(username, roleTitle);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getRoles().stream().anyMatch(r -> r.getTitle().equals(roleTitle))).isTrue();
    }

    @Test
    void loadUserByUsername() {
        AppUser user = new AppUser(null, "shahin@123", "123123", "shahin", new ArrayList<>());
        Role role = new Role(null, "SUPER_ADMIN");
        user.getRoles().add(role);
        String username = user.getUsername();
        User givenUserDetails = new User(username, user.getPassword(), user.getRoles().stream().map(
                r -> new SimpleGrantedAuthority(r.getTitle())
        ).collect(Collectors.toList()));
        when(userRepository.findAppUserByUsername(username)).thenReturn(Optional.of(user));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        UserDetails userDetails = underTest.loadUserByUsername(username);
        verify(userRepository).findAppUserByUsername(captor.capture());
        assertThat(captor.getValue()).isEqualTo(username);
        assertThat(userDetails).isEqualTo(givenUserDetails);
    }

    @Test
    void checkUserNotFoundException(){
        when(userRepository.findAppUserByUsername(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.loadUserByUsername(any())).isInstanceOf(RuntimeException.class)
                .hasMessage("entity not found");
    }
}