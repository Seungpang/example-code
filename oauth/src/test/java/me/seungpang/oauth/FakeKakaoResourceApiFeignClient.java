package me.seungpang.oauth;

import me.seungpang.oauth.auth.KakaoResourceApiFeignClient;
import me.seungpang.oauth.auth.KakaoUserResponse;
import me.seungpang.oauth.auth.KakaoUserResponse.KakaoAccountFeignResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class FakeKakaoResourceApiFeignClient implements KakaoResourceApiFeignClient {

    @Override
    public KakaoUserResponse getKakaoUser(
            final String authorizationHeader,
            final String contentTypeHeader,
            final Boolean secureResource,
            final String propertyKeys
    ) {
        try {
            final KakaoUserResponse.ProfileFeignResponse profileFeignResponse = getProfileFeignResponse();
            final KakaoAccountFeignResponse kakaoAccountFeignResponse
                    = getKakaoAccountFeignResponse(profileFeignResponse);
            return getKakaoUserFeignResponse(kakaoAccountFeignResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KakaoUserResponse.ProfileFeignResponse getProfileFeignResponse() throws Exception {
        final Class<KakaoUserResponse.ProfileFeignResponse> clazz = KakaoUserResponse.ProfileFeignResponse.class;
        final Constructor<KakaoUserResponse.ProfileFeignResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final KakaoUserResponse.ProfileFeignResponse instance = constructor.newInstance();

        final Field nicknameField = clazz.getDeclaredField("nickname");
        nicknameField.setAccessible(true);
        final Field profileImageUrlField = clazz.getDeclaredField("profileImageUrl");
        profileImageUrlField.setAccessible(true);

        nicknameField.set(instance, "testUserNickname");
        profileImageUrlField.set(instance, "testProfileImageUrl");
        return instance;
    }

    private KakaoAccountFeignResponse getKakaoAccountFeignResponse(final KakaoUserResponse.ProfileFeignResponse profile)
            throws Exception {
        final Class<KakaoAccountFeignResponse> clazz = KakaoAccountFeignResponse.class;
        final Constructor<KakaoAccountFeignResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final KakaoAccountFeignResponse instance = constructor.newInstance();

        final Field emailField = clazz.getDeclaredField("email");
        emailField.setAccessible(true);
        final Field profileField = clazz.getDeclaredField("profile");
        profileField.setAccessible(true);

        emailField.set(instance, "test@test.com");
        profileField.set(instance, profile);
        return instance;
    }

    private KakaoUserResponse getKakaoUserFeignResponse(final KakaoAccountFeignResponse kakaoAccount)
            throws Exception {
        final Class<KakaoUserResponse> clazz = KakaoUserResponse.class;
        final Constructor<KakaoUserResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final KakaoUserResponse instance = constructor.newInstance();

        final Field idField = clazz.getDeclaredField("id");
        idField.setAccessible(true);
        final Field kakaoAccountField = clazz.getDeclaredField("kakaoAccount");
        kakaoAccountField.setAccessible(true);

        idField.set(instance, 99_999_999L);
        kakaoAccountField.set(instance, kakaoAccount);
        return instance;
    }
}
