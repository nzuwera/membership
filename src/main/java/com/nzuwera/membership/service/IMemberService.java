package com.nzuwera.membership.service;

import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.utils.ResponseObject;

public interface IMemberService {

    ResponseObject createMember(MemberDto member);

    ResponseObject updateMember(String email, MemberDto member);

    ResponseObject findMemberByEmail(String email);

    ResponseObject findAllMembers();

    void deleteMember(String email) throws NotFoundException;
}