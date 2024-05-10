package me.seungpang.oauth.auth;

import me.seungpang.oauth.TestConfig;
import me.seungpang.oauth.auth.domain.dto.KakaoUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Import(TestConfig.class)
class FeignKakaoApiCallerTest {

    @Autowired
    private KakaoApiCaller feignKakaoApiCaller;

    @Test
    void 카카오_엑세스_토큰을_요청한다() {
        final String accessToken = feignKakaoApiCaller.getAccessToken(
                "authorizationCode",
                "kakaoOAuthRedirectUri",
                "clientId",
                "clientSecret"
        );

        assertThat(accessToken).isEqualTo("testKakaoAccessToken");
    }

    @Test
    void 엑세스_토큰으로_유저_정보를_요청한다() {
        final KakaoUserInfo kakaoUser = feignKakaoApiCaller.getKakaoUser("accessToken");

        assertAll(
                () -> assertThat(kakaoUser.getKakaoId()).isEqualTo(99_999_999L),
                () -> assertThat(kakaoUser.getNickname()).isEqualTo("testUserNickname")
        );
    }
}
