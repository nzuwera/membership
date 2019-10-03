package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.utils.ResponseObject;
public interface IMemberService {

    ResponseObject createMember(Member member) throws AlreadyExistsException;

    ResponseObject updateMember(Member member) throws NotFoundException;

    ResponseObject findMemberByEmail(String email);

    ResponseObject findAllMembers();

    void deleteMember(Member member);
}