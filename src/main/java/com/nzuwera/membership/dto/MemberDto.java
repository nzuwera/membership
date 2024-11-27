package com.nzuwera.membership.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.PlanType;
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
    private String firstName;

    /**
     * Member last name
     */
    private String lastName;
    /**
     * Member email
     */
    private String email;
    /**
     * Member data of birth
     */
    private Date dateOfBirth;
    /**
     * Member plan
     */
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
}
