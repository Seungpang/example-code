package me.seungpang.oauth.auth.application;

import me.seungpang.oauth.TestConfig;
import me.seungpang.oauth.auth.AuthTokenProvider;
import me.seungpang.oauth.auth.LoginMemberInfo;
import me.seungpang.oauth.auth.application.dto.KakaoLoginResult;
import me.seungpang.oauth.auth.domain.KakaoAccount;
import me.seungpang.oauth.auth.domain.KakaoAccountRepository;
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
public class KakaoLoginServiceTest {

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Autowired
    private KakaoAccountRepository kakaoAccountRepository;

    @Test
    void 가입하지_않은_카카오_유저가_로그인시_계정이_생성된다() {
        KakaoLoginResult kakaoLoginResult = kakaoLoginService.login("authorizationCode", "redirectUri");

        final LoginMemberInfo loginMemberInfo = authTokenProvider.getLoginMemberByAccessToken(kakaoLoginResult.getAccessToken());

        final KakaoAccount kakaoAccount = kakaoAccountRepository.findByKakaoId(99_999_999L).orElseThrow();

        assertThat(loginMemberInfo.getMemberId()).isEqualTo(kakaoAccount.getMember().getId());
    }

    @Test
    void 가입했던_적이_있으면_카카오_로그인시_기존_계정을_반환한다() {
        kakaoLoginService.login("autorizationCode", "redirectUri");

        final KakaoLoginResult kakaoLoginResult = kakaoLoginService.login("autorizationCode", "redirectUri");

        final LoginMemberInfo loginMemberInfo = authTokenProvider.getLoginMemberByAccessToken(kakaoLoginResult.getAccessToken());

        final KakaoAccount kakaoAccount = kakaoAccountRepository.findByKakaoId(99_999_999L).orElseThrow();

        assertThat(loginMemberInfo.getMemberId()).isEqualTo(kakaoAccount.getMember().getId());
    }
}
