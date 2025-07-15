package com.ssu.soundbridge.service;

import com.ssu.soundbridge.domain.User;
import com.ssu.soundbridge.domain.enums.Role;
import com.ssu.soundbridge.dto.UserDto;
import com.ssu.soundbridge.dto.UserSignUpDto;
import com.ssu.soundbridge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    //일반로그인 소셜 x
    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        log.info(userSignUpDto.getEmail());

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        if (!userSignUpDto.getPassword().equals(userSignUpDto.getConfirm_password())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }
        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        // ❶ 먼저 이메일 인증됐는지 확인
        if (!mailService.isVerified(userSignUpDto.getEmail())) {
            throw new IllegalStateException("이메일 인증을 완료해 주세요.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .age(userSignUpDto.getAge())
                .sex(userSignUpDto.getSex())
                .Genres(userSignUpDto.getGenres())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
        mailService.clearVerification(userSignUpDto.getEmail());
    }





    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getNickname())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }
} 