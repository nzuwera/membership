package com.nzuwera.membership.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProfileInfoRequest {
    /**
     * Member first name
     */
    @NotBlank(message = "firstName is required")
    private String firstName;

    /**
     * Member last name
     */
    @NotBlank(message = "lastName is required")
    private String lastName;
    /**
     * Member email
     */
    @Email(message = "email must be a valid email address")
    @NotBlank(message = "email is required")
    private String email;
    /**
     * Member password (plain for input only)
     */
    private String password;
    /**
     * Member date of birth
     */
    @Past(message = "dateOfBirth must be in the past")
    @NotNull(message = "dateOfBirth is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
