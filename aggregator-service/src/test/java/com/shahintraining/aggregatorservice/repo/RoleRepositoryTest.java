package com.shahintraining.aggregatorservice.repo;

import com.shahintraining.aggregatorservice.domain.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/14/2021
 */
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void findByTitle() {
        Role admin = new Role(null, "ADMIN");
        roleRepository.save(admin);
        Optional<Role> roleOptional = roleRepository.findByTitle(admin.getTitle());
        assertThat(roleOptional.isPresent()).isTrue();
        assertThat(admin).isEqualTo(roleOptional.get());
    }
}