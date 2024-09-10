package com.nzuwera.membership.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLAN")
public class Plan {
    /**
     * Plan Id
     */
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Plan name
     */
    @NotNull
    @Column(name = "NAME")
    private String name;

    /**
     * Plan start date
     */
    @NotNull
    @Column(name = "START_DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * Plan end date
     */
    @Column(name = "END_DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * Plan type
     */
    @NotNull
    @Column(name = "TYPE", nullable = false, columnDefinition = "varchar(10) default 'UNLIMITED'")
    @Enumerated(EnumType.STRING)
    private PlanType type;

}
