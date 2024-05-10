package me.seungpang.oauth;

import me.seungpang.oauth.auth.KakaoAccessTokenResponse;
import me.seungpang.oauth.auth.KakaoAuthApiFeignClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class FakeKakaoAuthApiFeignClient implements KakaoAuthApiFeignClient {

    @Override
    public KakaoAccessTokenResponse getAccessToken(
            final String grantType,
            final String clientId,
            final String redirectUri,
            final String authorizationCode,
            final String clientSecret) {
        try {
            return getKakaoAccessTokenFeignResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KakaoAccessTokenResponse getKakaoAccessTokenFeignResponse() throws Exception {
        final Class<KakaoAccessTokenResponse> clazz = KakaoAccessTokenResponse.class;
        final Constructor<KakaoAccessTokenResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        final KakaoAccessTokenResponse kakaoAccessTokenFeignResponse = constructor.newInstance();
        final Field accessTokenField = clazz.getDeclaredField("accessToken");
        accessTokenField.setAccessible(true);

        accessTokenField.set(kakaoAccessTokenFeignResponse, "testKakaoAccessToken");
        return kakaoAccessTokenFeignResponse;
    }
}
