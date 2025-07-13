package com.ssu.soundbridge.controller;

import com.ssu.soundbridge.dto.EmailDto;
import com.ssu.soundbridge.dto.VerifyDto;
import com.ssu.soundbridge.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/send-email") // 이 부분은 각자 바꿔주시면 됩니다.
    public ResponseEntity<?> emailCheck(@RequestBody EmailDto dto) throws MessagingException, UnsupportedEncodingException {
        mailService.sendSimpleMessage(dto.getEmail());
        return ResponseEntity.ok("인증 코드가 전송되었습니다.");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@Valid @RequestBody VerifyDto req) {
        boolean success = mailService.checkVerificationNumber(req.getEmail(), req.getCode());
        return success
                ? ResponseEntity.ok("이메일 인증 성공")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 올바르지 않습니다.");
    }
}
