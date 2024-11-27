package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.repository.MemberRepository;
import com.nzuwera.membership.utils.ResponseObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements IMemberService {


    private final MemberRepository repository;

    /**
     * Create new member
     *
     * @param memberDto new member
     * @return new created member
     * @throws AlreadyExistsException AlreadyExistsException
     */
    @Override
    public ResponseObject createMember(MemberDto memberDto) {
        repository.findByEmail(memberDto.getEmail()).map(member -> {
            throw new AlreadyExistsException(String.format("Member with email %s already exists", member.getEmail()));
        });
        Member member = MemberDto.toEntity(memberDto);
        Member newMember = repository.save(member);
        return ResponseObject.builder().data(newMember)
                .status(true)
                .errorCode(HttpStatus.CREATED.value())
                .message(String.format("Member with email %s created", member.getEmail()))
                .build();

    }

   /* @Override
    public ResponseObject updateMember(Member member) throws NotFoundException {
        if (repository.existsByEmail(member.getEmail())) {
            return Utils.setSuccessResponse(repository.save(member));
        } else {
            throw new NotFoundException(member.getEmail());
        }
    }

    @Override
    public ResponseObject findMemberByEmail(String email) {
        try {
            return Utils.setSuccessResponse(repository.findByEmail(email));
        } catch (EntityNotFoundException ex) {
            return new ResponseObject(new NotFoundException(ex.getMessage()));
        }
    }

    @Override
    public ResponseObject findAllMembers() {
        try {
            return Utils.setSuccessResponse(repository.findAll());
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }

    @Override
    public void deleteMember(Member member) {
        repository.delete(member);
    }*/
}