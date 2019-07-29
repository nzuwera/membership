package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;

import java.util.List;

public interface IMemberService {
    String NAME = "MemberService";

    Member createMember() throws AlreadyExistsException;

    Member updateMember(Member member);

    Member findMemberByEmail(String email) throws NotFoundException;

    List<Member> findAllMembers();

    void deleteMember(Member member);
}
