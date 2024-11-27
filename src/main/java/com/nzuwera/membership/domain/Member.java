package com.nzuwera.membership.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER")
public class Member {

    /**
     * Member Id
     */
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Member First name
     */
    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    /**
     * Member Last name
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

    @Column
    @Enumerated(EnumType.STRING)
    private PlanType plan;

}
