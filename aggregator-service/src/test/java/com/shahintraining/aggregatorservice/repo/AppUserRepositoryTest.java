package com.shahintraining.aggregatorservice.repo;

import com.shahintraining.aggregatorservice.domain.AppUser;
import com.shahintraining.aggregatorservice.domain.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/13/2021
 */
@DataJpaTest
class AppUserRepositoryTest {
    @Autowired
    private AppUserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }


    @Test
    void canFindUserByUsername(){
        AppUser saved = underTest.save(
                new AppUser(null, "beesba", "asldfkjlaewr", "beba", new ArrayList<Role>()));
        Optional<AppUser> found = underTest.findAppUserByUsername(saved.getUsername());
        assertThat(saved).isEqualTo(found.get());
    }


}