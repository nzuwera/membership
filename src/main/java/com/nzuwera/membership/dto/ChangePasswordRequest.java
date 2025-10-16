package com.nzuwera.membership.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePasswordRequest {
    @NotEmpty(message = "Old password is required")
    private String oldPassword;
    @NotEmpty(message = "New password is required")
    private String newPassword;
}
