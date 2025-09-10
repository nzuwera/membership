package com.nzuwera.membership.service;

import com.nzuwera.membership.config.AppProperties;
import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.repository.MemberRepository;
import com.nzuwera.membership.utils.ResponseObject;
import com.nzuwera.membership.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
class MemberService implements IMemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    public MemberService(MemberRepository repository, PasswordEncoder passwordEncoder, AppProperties appProperties) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.appProperties = appProperties;
    }

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
    public void deleteMember(String email) throws NotFoundException {
        // Get a current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        // Check if a user is trying to delete their own account
        if (email.equals(currentUserEmail)) {
            throw new IllegalArgumentException("Cannot delete your own account");
        }

        Member memberToDelete = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Member with email " + email + " not found"));

        // Check if trying to delete an admin and if it's the last admin
        if (memberToDelete.getRole() == com.nzuwera.membership.domain.Role.ADMIN) {
            long adminCount = repository.countByRole(com.nzuwera.membership.domain.Role.ADMIN);
            if (adminCount <= 1) {
                throw new IllegalArgumentException("Cannot delete the last admin user. At least one admin must remain");
            }
        }

        repository.delete(memberToDelete);
    }
}