package me.seungpang.oauth.auth.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoLoginResult {

    private String accessToken;
    private ZonedDateTime accessTokenExpireDateTime;
    private boolean isSignUp;

    @Builder
    public KakaoLoginResult(final String accessToken, final ZonedDateTime accessTokenExpireDateTime, boolean isSignUp) {
        this.accessToken = accessToken;
        this.accessTokenExpireDateTime = accessTokenExpireDateTime;
        this.isSignUp = isSignUp;
    }
}
