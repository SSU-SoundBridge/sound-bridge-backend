package com.ssu.soundbridge.dto;

import com.ssu.soundbridge.domain.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//회원가입
@NoArgsConstructor
@Getter
public class UserSignUpDto {
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirm_password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;


    private Integer age;              // 나이

    private Sex sex; //성별

    private List<String> genres; // 선호 장르 EX 블루스, 제즈, 팝

}
