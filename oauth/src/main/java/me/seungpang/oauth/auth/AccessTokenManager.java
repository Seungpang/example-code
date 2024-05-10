package me.seungpang.oauth.auth;

import me.seungpang.oauth.member.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class AccessTokenManager {

    private final AuthTokenProvider authTokenProvider;
    private final long accessTokenExpireMilliseconds;

    public AccessTokenManager(
            final AuthTokenProvider authTokenProvider,
            @Value("${security.jwt.token.access-token-expire-length}") final long accessTokenExpireMilliseconds
    ) {
        this.authTokenProvider = authTokenProvider;
        this.accessTokenExpireMilliseconds = accessTokenExpireMilliseconds;
    }

    public AccessTokenCreateInfo create(final Member member) {
        final ZonedDateTime accessTokenExpire = ZonedDateTime.now()
                .plus(accessTokenExpireMilliseconds, ChronoUnit.MILLIS);
        final String accessToken = authTokenProvider.createAccessToken(
                member.getId(),
                accessTokenExpire
        );
        return new AccessTokenCreateInfo(accessToken, accessTokenExpire);
    }
}
