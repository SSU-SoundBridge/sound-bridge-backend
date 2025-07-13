package com.ssu.soundbridge.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginPageContoller {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakao_client_id;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakao_redirect_uri;

    @GetMapping("/page")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+kakao_client_id+"&redirect_uri="+kakao_redirect_uri;
        model.addAttribute("location", location);
        return "login";
    }
}
