package com.nzuwera.membership.service;

import com.nzuwera.membership.dto.ChangePasswordRequest;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.dto.UpdateProfileInfoRequest;
import com.nzuwera.membership.utils.ResponseObject;
import jakarta.validation.Valid;

public interface IMemberService {

    ResponseObject createMember(MemberDto member);

    ResponseObject updateMember(String email, MemberDto member);
    ResponseObject updateInfo(String email, UpdateProfileInfoRequest member);

    ResponseObject findMemberByEmail(String email);

    ResponseObject findAllMembers();

    void deleteMember(String email);

    ResponseObject updatePassword(String username, @Valid ChangePasswordRequest changePasswordRequest);
}