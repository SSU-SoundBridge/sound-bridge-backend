package com.ssu.soundbridge.controller;

import com.ssu.soundbridge.dto.UserDto;
import com.ssu.soundbridge.dto.UserInfoDto;
import com.ssu.soundbridge.dto.UserSignUpDto;
import com.ssu.soundbridge.service.JwtService;
import com.ssu.soundbridge.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> singUp(@Valid @RequestBody UserSignUpDto userSignUpDto) throws Exception {

        userService.signUp(userSignUpDto);
        return ResponseEntity.ok("회원가입 완료");
    }
    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getMyInfo(HttpServletRequest request) {
        return jwtService.getUserFromAccessToken(request)
                .map(user -> ResponseEntity.ok(UserInfoDto.from(user)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
