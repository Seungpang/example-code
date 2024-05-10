package me.seungpang.oauth.auth;

import me.seungpang.oauth.member.Member;
import me.seungpang.oauth.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccessTokenManagerTest {

    @Autowired
    private AccessTokenManager accessTokenManager;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 액세스_토큰을_생성한다() {
        final Member member = Member.builder()
                .nickname("testMember")
                .birthDate(LocalDate.of(2024,2,1))
                .anniversary(LocalDate.of(2024,2,2))
                .build();
        final Member savedMember = memberRepository.save(member);

        final AccessTokenCreateInfo accessTokenCreateInfo = accessTokenManager.create(member);

        final LoginMemberInfo loginMember = authTokenProvider.getLoginMemberByAccessToken(
                accessTokenCreateInfo.getAccessToken());

        assertThat(loginMember.getMemberId()).isEqualTo(savedMember.getId());
    }
}
