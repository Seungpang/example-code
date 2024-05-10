package me.seungpang.oauth.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungpang.oauth.auth.application.dto.KakaoLoginResult;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResultResponse {

    private String accessToken;
    private ZonedDateTime accessTokenExpireDateTime;

    @Builder
    public LoginResultResponse(
            final String accessToken,
            final ZonedDateTime accessTokenExpireDateTime
    ) {
        this.accessToken = accessToken;
        this.accessTokenExpireDateTime = accessTokenExpireDateTime;
    }

    public static LoginResultResponse from(final KakaoLoginResult loginResult) {
        return LoginResultResponse.builder()
                .accessToken(loginResult.getAccessToken())
                .accessTokenExpireDateTime(loginResult.getAccessTokenExpireDateTime())
                .build();
    }
}
