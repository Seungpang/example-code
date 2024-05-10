package me.seungpang.oauth.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoLoginRequest {

    private String authorizationCode;
    private String redirectUri;

    public KakaoLoginRequest(final String authorizationCode, final String redirectUri) {
        this.authorizationCode = authorizationCode;
        this.redirectUri = redirectUri;
    }
}
