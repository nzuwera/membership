package com.nzuwera.membership.repository;

import com.nzuwera.membership.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<Plan, UUID> {
    Boolean existsByName(String name);
    Plan findByName(String name);
}
