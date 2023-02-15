package com.monalisa.domain.member.service;

import com.monalisa.domain.book.repository.UserRepository;
import com.monalisa.domain.member.domain.User;
import com.monalisa.domain.member.dto.AddMemberRequestDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final UserRepository userRepository;

    public void signup(final AddMemberRequestDto addMemberRequestDto) {
        User newUser = User.of(addMemberRequestDto.getName());
        userRepository.save(newUser);
    }
}
