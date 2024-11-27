package com.nzuwera.membership.service;

import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.utils.ResponseObject;
public interface IMemberService {

    ResponseObject createMember(MemberDto member);

 /*   ResponseObject updateMember(Member member) throws NotFoundException;

    ResponseObject findMemberByEmail(String email);

    ResponseObject findAllMembers();

    void deleteMember(Member member);*/
}