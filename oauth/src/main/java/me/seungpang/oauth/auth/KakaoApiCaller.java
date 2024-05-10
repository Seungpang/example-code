package me.seungpang.oauth.auth;

import me.seungpang.oauth.auth.domain.dto.KakaoUserInfo;

public interface KakaoApiCaller {

    String getAccessToken(
            String authorizationCode,
            String kakaoOAuthRedirectUri,
            String kakaoOAuthClientId,
            String kakaoOAuthClientSecret
    );

    KakaoUserInfo getKakaoUser(String kakaoAccessToken);
}
