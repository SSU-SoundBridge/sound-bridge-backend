package com.ssu.soundbridge.dto;
import com.ssu.soundbridge.domain.User;
import com.ssu.soundbridge.domain.enums.Role;
import com.ssu.soundbridge.domain.enums.Sex;
import com.ssu.soundbridge.domain.enums.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoDto {
    private Long id;
    private String email;
    private String nickname;
    private String imageUrl;
    private Integer age;
    private Sex sex;
    private List<String> genres;
    private Role role;

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .age(user.getAge())
                .sex(user.getSex())
                .genres(user.getGenres())
                .role(user.getRole())
                .build();
    }
}
