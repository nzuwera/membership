package com.nzuwera.membership.domain;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "MEMBER")
public class Member {

    /**
     * Member Id
     */
    @NotNull
    @Id
    @Column(name = "ID", nullable = false)
    @Type(type = "pg-uuid")
    private UUID id;

    /**
     * Member firstname
     */
    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    /**
     * Member lastname
     */
    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;
    /**
     * Member email
     */
    @NotNull
    @Column(name = "EMAIL")
    private String email;
    /**
     * Member data of birth
     */
    @NotNull
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn
    private Plan plan;

    public Member() {
    }

    public Member(@NotNull UUID id, @NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull Date dateOfBirth, Plan plan) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.plan = plan;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(firstName, member.firstName) &&
                Objects.equals(lastName, member.lastName) &&
                Objects.equals(email, member.email) &&
                Objects.equals(dateOfBirth, member.dateOfBirth) &&
                Objects.equals(plan, member.plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, dateOfBirth, plan);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", plan=" + plan +
                '}';
    }
}
