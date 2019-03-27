package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;

import java.util.List;

public interface IMemberService {
    String NAME = "MemberService";
    Member createMember();
    Member updateMember(Member member);
    Member findMemberByLastNameAndFirstName(String lastName);
    List<Member> findAllMembers();
    Void deleteMember(Member member);
}
