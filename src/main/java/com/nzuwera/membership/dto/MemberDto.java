package com.nzuwera.membership.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.MemberStatus;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.domain.Role;
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
public class MemberDto {
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
    /**
     * Member plan
     */
    @NotNull(message = "plan is required")
    @JsonProperty("plan")
    private PlanType planName;

    private Role role;
    private MemberStatus status;

   public static Member toEntity(MemberDto memberDto) {
       Member member = new Member();
       member.setFirstName(memberDto.getFirstName());
       member.setLastName(memberDto.getLastName());
       member.setEmail(memberDto.getEmail());
       member.setDateOfBirth(memberDto.getDateOfBirth());
       member.setPlan(memberDto.getPlanName());
       member.setRole(memberDto.getRole());
       member.setStatus(memberDto.getStatus());
       // Set password as provided; in production it should be encoded by service layer.
       member.setPassword(memberDto.getPassword());
       return member;
   }

   public static MemberDto fromEntity(Member member) {
       return MemberDto.builder()
               .firstName(member.getFirstName())
               .lastName(member.getLastName())
               .email(member.getEmail())
               .dateOfBirth(member.getDateOfBirth())
               .planName(member.getPlan())
               .role(member.getRole())
               .status(member.getStatus())
               .build();
   }
}
