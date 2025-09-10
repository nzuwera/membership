package com.nzuwera.membership.config;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.MemberStatus;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.domain.Role;
import com.nzuwera.membership.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Configuration
class BootstrapConfig {

    private static final Logger log = LoggerFactory.getLogger(BootstrapConfig.class);

    private final AppProperties appProperties;

    BootstrapConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    ApplicationRunner seedDefaultAdmin(MemberRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = appProperties.getDefaultAdminUsername(); // using username field stored as email column per current domain
            repository.findByEmail(adminEmail).ifPresentOrElse(m -> {
                log.info("Default admin already exists: {}", adminEmail);
            }, () -> {
                Member admin = new Member();
                admin.setFirstName("Super");
                admin.setLastName("Administrator");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(appProperties.getDefaultAdminPassword()));
                // set a safe default DOB
                LocalDate dob = LocalDate.of(1990, 1, 1);
                admin.setDateOfBirth(Date.from(dob.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                // choose a default plan (any valid enum)
                admin.setPlan(PlanType.UNLIMITED);
                admin.setRole(Role.ADMIN);
                admin.setStatus(MemberStatus.ACTIVE);
                repository.save(admin);
                log.info("Default admin user created: {}", adminEmail);
            });
        };
    }
}
