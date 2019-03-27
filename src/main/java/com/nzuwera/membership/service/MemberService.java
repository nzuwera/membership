package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(IMemberService.NAME)
public class MemberService implements IMemberService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member createMember() {
        return null;
    }

    @Override
    public Member updateMember(Member member) {
        return null;
    }

    @Override
    public Member findMemberByLastNameAndFirstName(String lastName) {
        return null;
    }

    @Override
    public List<Member> findAllMembers() {
        return null;
    }

    @Override
    public Void deleteMember(Member member) {
        return null;
    }
}
