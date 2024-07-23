package me.seungpang.oauth.auth;

import lombok.RequiredArgsConstructor;
import me.seungpang.oauth.auth.domain.dto.KakaoUserInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestClientKakaoApiCaller implements KakaoApiCaller {

    private final KakaoApiHttpClient kakaoApiHttpClient;

    @Override
    public String getAccessToken(
            final String authorizationCode,
            final String kakaoOAuthRedirectUri,
            final String kakaoOAuthClientId,
            final String kakaoOAuthClientSecret
    ) {
        return kakaoApiHttpClient.getAccessToken(
                "authorization_code",
                kakaoOAuthClientId,
                kakaoOAuthRedirectUri,
                authorizationCode,
                kakaoOAuthClientSecret
        ).getAccessToken();
    }

    @Override
    public KakaoUserInfo getKakaoUser(final String kakaoAccessToken) {
        final String authorizationHeader = "Bearer " + kakaoAccessToken;
        final String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        final String propertyKeys = "[\"kakao_account.profile\", \"kakao_account.name\", \"kakao_account.email\"]";
        final KakaoUserResponse kakaoUser = kakaoApiHttpClient.getKakaoUser(
                authorizationHeader,
                contentType,
                Boolean.TRUE,
                propertyKeys
        );

        return new KakaoUserInfo(
                kakaoUser.getId(),
                kakaoUser.getNickname()
        );
    }
}
