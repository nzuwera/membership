package com.nzuwera.membership.config;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.MemberStatus;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.domain.Role;
import com.nzuwera.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
class BootstrapConfig {

    private static final Logger log = LoggerFactory.getLogger(BootstrapConfig.class);
    private final AppProperties appProperties;

    @Bean
    ApplicationRunner seedDefaultAdmin(MemberRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Validate configuration
            String adminUsername = appProperties.getDefaultAdminUsername();
            String adminPassword = appProperties.getDefaultAdminPassword();

            if (adminUsername == null || adminUsername.isBlank()) {
                log.warn("Default admin username is not configured. Skipping admin seed.");
                return;
            }

            if (adminPassword == null || adminPassword.isBlank()) {
                log.error("Default admin password is not configured. Cannot create default admin user.");
                throw new IllegalStateException("Default admin password must be configured in application properties");
            }

            // using username field stored as email column per current domain
            repository.findByEmail(adminUsername).ifPresentOrElse(m -> log.info("Default admin already exists: {}", adminUsername), () -> {
                Member admin = new Member();
                admin.setFirstName("SUPER");
                admin.setLastName("ADMIN");
                admin.setEmail(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                // set a safe default DOB
                LocalDate dob = LocalDate.of(1990, 1, 1);
                admin.setDateOfBirth(dob);
                // choose a default plan (any valid enum)
                admin.setPlan(PlanType.SILVER);
                admin.setRole(Role.ADMIN);
                admin.setStatus(MemberStatus.ACTIVE);
                repository.save(admin);
                log.info("Default admin user created: {}", adminUsername);
            });
        };
    }
}
