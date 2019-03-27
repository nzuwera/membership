package com.nzuwera.membership.dto;

import java.util.Date;
import java.util.Objects;

public class MemberDto {
    /**
     * Member firstname
     */
    private String firstName;

    /**
     * Member lastname
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
    private String planName;

    public MemberDto() {
    }

    public MemberDto(String firstName, String lastName, String email, Date dateOfBirth, String planName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.planName = planName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDto memberDto = (MemberDto) o;
        return Objects.equals(firstName, memberDto.firstName) &&
                Objects.equals(lastName, memberDto.lastName) &&
                Objects.equals(email, memberDto.email) &&
                Objects.equals(dateOfBirth, memberDto.dateOfBirth) &&
                Objects.equals(planName, memberDto.planName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, dateOfBirth, planName);
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", planName='" + planName + '\'' +
                '}';
    }
}
