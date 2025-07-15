package com.ssu.soundbridge.domain;

import com.ssu.soundbridge.domain.enums.Role;
import com.ssu.soundbridge.domain.enums.Sex;
import com.ssu.soundbridge.domain.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password; // 비밀번호
    private String imageUrl; // 프로필 이미지
    @Column(nullable = false, unique = true)
    private String nickname; //닉네임
    private Integer age; //나이
    @Enumerated(value = EnumType.STRING)
    private Sex sex; //성별
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> Genres; //성호 장르
    private String refreshToken; // 리프레시 토큰


    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }
    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
} 