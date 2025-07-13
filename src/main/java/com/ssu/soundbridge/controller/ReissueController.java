package com.ssu.soundbridge.controller;

import com.ssu.soundbridge.repository.UserRepository;
import com.ssu.soundbridge.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ReissueController {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // 1. RefreshToken 추출
        String refreshToken = jwtService.extractRefreshToken(request).orElse(null);
        if (refreshToken == null || !jwtService.isTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("유효하지 않은 리프레시 토큰입니다.");
        }

        // 2. DB에 저장된 RefreshToken과 일치하는 유저 조회
        return userRepository.findByRefreshToken(refreshToken)
                .map(user -> {
                    // 3. 새로운 토큰 생성
                    String newAccessToken = jwtService.createAccessToken(user.getEmail());
                    String newRefreshToken = jwtService.createRefreshToken();

                    // 4. DB에 refresh 토큰 갱신
                    user.updateRefreshToken(newRefreshToken);
                    userRepository.saveAndFlush(user);

                    // 5. 새 토큰을 응답 헤더에 담아 전송
                    jwtService.sendAccessAndRefreshToken(response, newAccessToken, newRefreshToken);
                    return ResponseEntity.ok().body("토큰이 재발급되었습니다.");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("유저를 찾을 수 없습니다."));
    }
}
