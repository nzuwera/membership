package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.repository.MemberRepository;
import com.nzuwera.membership.utils.ResponseObject;
import com.nzuwera.membership.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberService implements IMemberService {


    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Create new member
     *
     * @param member new member
     * @return new created member
     * @throws AlreadyExistsException AlreadyExistsException
     */
    @Override
    public ResponseObject createMember(Member member) throws AlreadyExistsException {
        if (memberRepository.existsByEmail(member.getEmail())) {
            return Utils.setSuccessResponse(memberRepository.save(member));
        } else {
            throw new AlreadyExistsException(member.getEmail());
        }
    }

    @Override
    public ResponseObject updateMember(Member member) throws NotFoundException {
        if (memberRepository.existsByEmail(member.getEmail())) {
            return Utils.setSuccessResponse(memberRepository.save(member));
        } else {
            throw new NotFoundException(member.getEmail());
        }
    }

    @Override
    public ResponseObject findMemberByEmail(String email) {
        try {
            return Utils.setSuccessResponse(memberRepository.findByEmail(email));
        } catch (EntityNotFoundException ex) {
            return new ResponseObject(new NotFoundException(ex.getMessage()));
        }
    }

    @Override
    public ResponseObject findAllMembers() {
        try {
            return Utils.setSuccessResponse(memberRepository.findAll());
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}