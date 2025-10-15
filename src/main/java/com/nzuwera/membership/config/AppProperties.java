package com.nzuwera.membership.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.membership")
@Setter
@Getter
public class AppProperties {

    @Min(value = 1, message = "expiry-date must be at least 1 day")
    private int expiryDate;

    @NotEmpty(message = "default-password must not be empty")
    private String defaultPassword;
    private String defaultAdminUsername;
    private String defaultAdminPassword;

}
