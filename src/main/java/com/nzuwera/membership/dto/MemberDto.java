package com.nzuwera.membership.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.PlanType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
     * Member date of birth
     */
    @Past(message = "dateOfBirth must be in the past")
    @NotNull(message = "dateOfBirth is required")
    private Date dateOfBirth;
    /**
     * Member plan
     */
    @NotNull(message = "plan is required")
    @JsonProperty("plan")
    private PlanType planName;

   public static Member toEntity(MemberDto memberDto) {
       Member member = new Member();
       member.setFirstName(memberDto.getFirstName());
       member.setLastName(memberDto.getLastName());
       member.setEmail(memberDto.getEmail());
       member.setDateOfBirth(memberDto.getDateOfBirth());
       member.setPlan(memberDto.getPlanName());
       return member;
   }

   public static MemberDto fromEntity(Member member) {
       return MemberDto.builder()
               .firstName(member.getFirstName())
               .lastName(member.getLastName())
               .email(member.getEmail())
               .dateOfBirth(member.getDateOfBirth())
               .planName(member.getPlan())
               .build();
   }
}
