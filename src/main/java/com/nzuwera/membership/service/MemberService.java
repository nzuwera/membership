package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.repository.MemberRepository;
import com.nzuwera.membership.utils.ResponseObject;
import com.nzuwera.membership.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
class MemberService implements IMemberService {

    private final MemberRepository repository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    /**
     * Create new member
     *
     * @param memberDto new member
     * @return new created member
     * @throws AlreadyExistsException AlreadyExistsException
     */
    @Override
    @Transactional
    public ResponseObject createMember(MemberDto memberDto) {
        repository.findByEmail(memberDto.getEmail()).ifPresent(member -> {
            throw new AlreadyExistsException(String.format("Member with email %s already exists", member.getEmail()));
        });
        Member member = MemberDto.toEntity(memberDto);
        // encode password and set defaults for role/status if null
        if (memberDto.getPassword() != null && !memberDto.getPassword().isBlank()) {
            member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        }
        if (member.getRole() == null) {
            member.setRole(com.nzuwera.membership.domain.Role.USER);
        }
        if (member.getStatus() == null) {
            member.setStatus(com.nzuwera.membership.domain.MemberStatus.ACTIVE);
        }
        Member newMember = repository.save(member);
        return ResponseObject.builder()
                .data(MemberDto.fromEntity(newMember))
                .status(true)
                .errorCode(HttpStatus.CREATED.value())
                .message(String.format("Member with email %s created", member.getEmail()))
                .build();

    }

    @Override
    @Transactional
    public ResponseObject updateMember(String email, MemberDto memberDto) {
        try {
            Member existing = repository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException(email));
            // update fields
            existing.setFirstName(memberDto.getFirstName());
            existing.setLastName(memberDto.getLastName());
            existing.setDateOfBirth(memberDto.getDateOfBirth());
            existing.setPlan(memberDto.getPlanName());
            // allow updating email if provided
            existing.setEmail(memberDto.getEmail());
            // update role/status if provided
            if (memberDto.getRole() != null) existing.setRole(memberDto.getRole());
            if (memberDto.getStatus() != null) existing.setStatus(memberDto.getStatus());
            // update password if provided and non-blank
            if (memberDto.getPassword() != null && !memberDto.getPassword().isBlank()) {
                existing.setPassword(passwordEncoder.encode(memberDto.getPassword()));
            }
            Member saved = repository.save(existing);
            return Utils.setSuccessResponse(MemberDto.fromEntity(saved));
        } catch (NotFoundException ex) {
            return new ResponseObject(ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject findMemberByEmail(String email) {
        try {
            Member member = repository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException(email));
            return Utils.setSuccessResponse(MemberDto.fromEntity(member));
        } catch (NotFoundException ex) {
            return new ResponseObject(ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject findAllMembers() {
        try {
            List<MemberDto> members = repository.findAll().stream()
                    .map(MemberDto::fromEntity)
                    .collect(Collectors.toList());
            return Utils.setSuccessResponse(members);
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }

    @Override
    @Transactional
    public void deleteMember(String email) {
        repository.findByEmail(email).ifPresent(repository::delete);
    }
}