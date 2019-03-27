package com.nzuwera.membership.domain;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "PLAN")
public class Plan {
    /**
     * Plan Id
     */
    @NotNull
    @Id
    @Column(name = "ID", nullable = false)
    @Type(type = "pg-uuid")
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

    public Plan() {
    }

    public Plan(@NotNull UUID id, @NotNull String name, @NotNull Date startDate, @NotNull Date endDate, @NotNull @NotBlank PlanType type) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return Objects.equals(id, plan.id) &&
                Objects.equals(name, plan.name) &&
                Objects.equals(startDate, plan.startDate) &&
                Objects.equals(endDate, plan.endDate) &&
                type == plan.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, type);
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", type=" + type +
                '}';
    }
}
