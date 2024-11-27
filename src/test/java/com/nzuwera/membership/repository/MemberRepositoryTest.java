package com.nzuwera.membership.repository;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.5-alpine");

    @Test
    void connectionEstablished() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MemberDto memberDto1 = MemberDto.builder()
                .email("test1@email.com")
                .dateOfBirth(new Date(1984,6,26))
                .firstName("John")
                .lastName("Doe")
                .planName(PlanType.UNLIMITED)
                .build();
        MemberDto memberDto2 = MemberDto.builder()
                .email("test2@email.com")
                .dateOfBirth(new Date(1984,6,26))
                .firstName("John")
                .lastName("Doe")
                .planName(PlanType.LIMITED)
                .build();
        Member member1 = MemberDto.toEntity(memberDto1);
        Member member2 = MemberDto.toEntity(memberDto2);
        memberRepository.saveAll(List.of(member1, member2));
    }

    @Test
    void testFindByEmail() {
        Optional<Member> member = memberRepository.findByEmail("test1@email.com");
        assertThat(member.isPresent()).isTrue();
        assertThat(member.get().getEmail()).isEqualTo("test1@email.com");
        assertThat(member.get().getDateOfBirth()).isNotNull();
        assertThat(member.get().getDateOfBirth()).isInstanceOf(Date.class);
        assertThat(member.get().getDateOfBirth()).isEqualTo(new Date(1984,6,26));
        assertThat(member.get().getFirstName()).isEqualTo("John");
        assertThat(member.get().getLastName()).isEqualTo("Doe");
        assertThat(member.get().getPlan()).isEqualTo(PlanType.UNLIMITED);
    }
}