package me.seungpang.oauth.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seungpang.oauth.auth.AccessTokenCreateInfo;
import me.seungpang.oauth.auth.AccessTokenManager;
import me.seungpang.oauth.auth.KakaoApiCaller;
import me.seungpang.oauth.auth.application.dto.KakaoLoginResult;
import me.seungpang.oauth.auth.domain.KakaoAccount;
import me.seungpang.oauth.auth.domain.KakaoAccountRepository;
import me.seungpang.oauth.auth.domain.dto.KakaoUserInfo;
import me.seungpang.oauth.member.Member;
import me.seungpang.oauth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class KakaoLoginService {

    private final MemberRepository memberRepository;
    private final KakaoAccountRepository kakaoAccountRepository;
    private final KakaoApiCaller kakaoApiCaller;
    private final String kakaoOAuthClientId;
    private final String kakaoOAuthClientSecret;
    private final AccessTokenManager accessTokenManager;

    public KakaoLoginService(
            final MemberRepository memberRepository,
            final KakaoAccountRepository kakaoAccountRepository,
            final KakaoApiCaller kakaoApiCaller,
            @Value("${oauth.kakao.clientId}") final String kakaoOAuthClientId,
            @Value("${oauth.kakao.clientSecret}") final String kakaoOAuthClientSecret,
            final AccessTokenManager accessTokenManager
    ) {
        this.memberRepository = memberRepository;
        this.kakaoAccountRepository = kakaoAccountRepository;
        this.kakaoApiCaller = kakaoApiCaller;
        this.kakaoOAuthClientId = kakaoOAuthClientId;
        this.kakaoOAuthClientSecret = kakaoOAuthClientSecret;
        this.accessTokenManager = accessTokenManager;
    }

    @Transactional
    public KakaoLoginResult login(final String authorizationCode, final String redirectUri) {
        final String kakaoAccessToken = getKakaoAccessToken(authorizationCode, redirectUri);
        final KakaoUserInfo kakaoUser = kakaoApiCaller.getKakaoUser(kakaoAccessToken);
        log.info("카카오 유저 정보 가져오기 성공");
        final KakaoAccount kakaoAccount = kakaoAccountRepository.findByKakaoId(kakaoUser.getKakaoId())
                .orElseGet(() -> {
                    final Member savedMember = memberRepository.save(Member.builder().nickname(kakaoUser.getNickname()).build());
                    return saveKakaoAccount(kakaoUser, savedMember);
                });

        final Member member = kakaoAccount.getMember();
        final AccessTokenCreateInfo accessTokenCreateInfo = accessTokenManager.create(member);
        return new KakaoLoginResult(accessTokenCreateInfo.getAccessToken(), accessTokenCreateInfo.getExpire(), kakaoAccount.isSignUp());
    }

    private String getKakaoAccessToken(final String authorizationCode, final String redirectUri) {
        return kakaoApiCaller.getAccessToken(
                authorizationCode,
                redirectUri,
                kakaoOAuthClientId,
                kakaoOAuthClientSecret
        );
    }

    private KakaoAccount saveKakaoAccount(final KakaoUserInfo kakaoUser, final Member savedMember) {
        final KakaoAccount kakaoAccount = new KakaoAccount(kakaoUser.getKakaoId(), savedMember, false);
        return kakaoAccountRepository.save(kakaoAccount);
    }
}
