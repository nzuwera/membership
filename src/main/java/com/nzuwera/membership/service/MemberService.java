package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(IMemberService.NAME)
public class MemberService implements IMemberService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member createMember(Member member) throws AlreadyExistsException {
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Member member) {
        return null;
    }

    @Override
    public Member findMemberByEmail(String email) throws NotFoundException {
        if (memberRepository.existsByEmail(email)) {
            return memberRepository.findByEmail(email);
        } else {
            throw new NotFoundException(email);
        }
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}