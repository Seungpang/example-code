package me.seungpang.oauth.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtAuthTokenProviderTest {

    private SecretKey key;
    private JwtAuthTokenProvider jwtAuthTokenProvider;
    private JwtAuthTokenProvider invalidTokenJwtAuthTokenProvider;

    @BeforeEach
    void setUp() {
        final String secretKey = "testSecretkeytestSecretkeytestSecretkeytestSecretkey";
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        jwtAuthTokenProvider = new JwtAuthTokenProvider(secretKey);
        final String invalidSecretKey = "invalidSecretKeyinvalidSecretKeyinvalidSecretKeyinvalidSecretKey";
        invalidTokenJwtAuthTokenProvider = new JwtAuthTokenProvider(invalidSecretKey);
    }

    @Test
    void accessToken을_생성한다() {
        final String accessToken = jwtAuthTokenProvider.createAccessToken(1L, ZonedDateTime.now().plusSeconds(5));

        final Claims payload = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();

        assertThat(payload.get("memberId", Long.class)).isEqualTo(1L);
    }

    @Test
    void 생성된_액세스_토큰을_멤버_정보로_파싱한다() {
        final String accessToken
                = jwtAuthTokenProvider.createAccessToken(1L, ZonedDateTime.now().plusSeconds(2));

        final LoginMemberInfo loginMember = jwtAuthTokenProvider.getLoginMemberByAccessToken(accessToken);

        assertThat(loginMember.getMemberId()).isEqualTo(1L);
    }

    @Test
    void 다른_키로_생성된_액세스_토큰을_파싱하면_에러가_발생한다() {
        final String invalidAccessToken = invalidTokenJwtAuthTokenProvider.createAccessToken(
                1L,
                ZonedDateTime.now().plusSeconds(5)
        );

        assertThatThrownBy(() -> jwtAuthTokenProvider.getLoginMemberByAccessToken(invalidAccessToken))
                .isInstanceOf(JwtInvalidSecretKeyException.class);
    }

    @Test
    void 만료된_액세스_토큰을_파싱하면_에러가_발생한다() {
        final String expiredAccessToken
                = jwtAuthTokenProvider.createAccessToken(1L, ZonedDateTime.now().minusNanos(1L));

        assertThatThrownBy(() -> jwtAuthTokenProvider.getLoginMemberByAccessToken(expiredAccessToken))
                .isInstanceOf(Exception.class);
    }

    @Test
    void 액세스_토큰으로_파싱이_안되는_문자열이_들어오면_에러가_발생한다() {
        assertThatThrownBy(() -> jwtAuthTokenProvider.getLoginMemberByAccessToken("파싱이안되는문자열"))
                .isInstanceOf(JwtInvalidFormException.class);
    }
}
