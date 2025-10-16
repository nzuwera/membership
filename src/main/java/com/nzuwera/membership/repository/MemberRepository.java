package com.nzuwera.membership.repository;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.dto.UpdateProfileInfoRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Member as m set m.firstName = :#{#request.firstName}, m.lastName = :#{#request.lastName}, m.email = :#{#request.email}, m.dateOfBirth = :#{#request.dateOfBirth} where m.email = :email")
    int updateInfo(@Param("email") String email, @Param("request") UpdateProfileInfoRequest profileInfoRequest);

    @Modifying(clearAutomatically = true)
    @Query("update Member as m set m.password = :password where m.email = :email")
    int changePassword(@Param("email") String email, @Param("password") String password);

}
