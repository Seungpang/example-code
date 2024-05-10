package me.seungpang.oauth.auth.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoUserInfo {

    private final Long kakaoId;
    private final String nickname;
}
