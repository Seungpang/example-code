package me.seungpang.oauth.auth;

import lombok.RequiredArgsConstructor;
import me.seungpang.oauth.auth.domain.dto.KakaoUserInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeginKakaoApiCaller implements KakaoApiCaller {

    private final KakaoAuthApiFeignClient authApiFeignClient;
    private final KakaoResourceApiFeignClient resourceApiFeignClient;

    @Override
    public String getAccessToken(
            final String authorizationCode,
            final String kakaoOAuthRedirectUri,
            final String kakaoOAuthClientId,
            final String kakaoOAuthClientSecret
    ) {
        return authApiFeignClient.getAccessToken(
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
        final KakaoUserResponse kakaoUser = resourceApiFeignClient.getKakaoUser(
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
