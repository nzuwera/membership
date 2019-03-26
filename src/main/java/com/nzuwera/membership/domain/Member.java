package com.nzuwera.membership.domain;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
     * Member data of birth
     */
    @NotNull
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    public Member() {
    }

    /**
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     */
    public Member(@NotNull UUID id, @NotNull String firstName, @NotNull String lastName, @NotNull Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
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
                Objects.equals(dateOfBirth, member.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
