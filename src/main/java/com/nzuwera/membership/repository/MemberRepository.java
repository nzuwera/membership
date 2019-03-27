package com.nzuwera.membership.repository;

import com.nzuwera.membership.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository(value = "MemberRepository")
public interface MemberRepository extends JpaRepository<Member, UUID> {

    boolean existsByEmail(String email);
    Member findByEmail(String email);

}
