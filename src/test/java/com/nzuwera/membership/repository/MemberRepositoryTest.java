package com.nzuwera.membership.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Test
    void existsByEmail() {
    }

    @Test
    void findByEmail() {
    }
}