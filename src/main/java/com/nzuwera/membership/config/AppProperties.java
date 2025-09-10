package com.nzuwera.membership.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.membership")
public class AppProperties {

    @Min(value = 1, message = "expiry-date must be at least 1 day")
    private int expiryDate;
    private String defaultAdminUsername;
    private String defaultAdminPassword;

    int getExpiryDate() {
        return expiryDate;
    }

    void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }

    String getDefaultAdminUsername() {
        return defaultAdminUsername;
    }
    void setDefaultAdminUsername(String defaultAdminUsername){
        this.defaultAdminUsername = defaultAdminUsername;
    }

    String getDefaultAdminPassword(){
        return defaultAdminPassword;
    }
    void setDefaultAdminPassword(String defaultAdminPassword){
        this.defaultAdminPassword = defaultAdminPassword;
    }
}
