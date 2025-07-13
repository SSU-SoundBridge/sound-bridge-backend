package com.ssu.soundbridge.controller;

import com.ssu.soundbridge.dto.KakaoUserInfoResponseDto;
import com.ssu.soundbridge.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final KakaoService kakaoService;
    //리다이렉트 url
    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<?> callback(@RequestParam("code") String code) throws IOException {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);//access 토큰
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken); //유저정보
        // 여기에 서버 사용자 로그인(인증) 또는 회원가입 로직 추가
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
