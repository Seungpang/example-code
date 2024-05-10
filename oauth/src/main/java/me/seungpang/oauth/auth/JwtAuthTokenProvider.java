package me.seungpang.oauth.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtAuthTokenProvider implements AuthTokenProvider {

    private final SecretKey key;

    public JwtAuthTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createAccessToken(final Long memberId, final ZonedDateTime accessTokenExpire) {
        final Date now = new Date();
        final Date expiration = Date.from(accessTokenExpire.toInstant());
        final Claims claims = Jwts.claims()
                .add("memberId", memberId)
                .build();

        return Jwts.builder()
                .signWith(key)
                .subject("togetherMap access token")
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .compact();
    }

    @Override
    public LoginMemberInfo getLoginMemberByAccessToken(final String accessToken) {
        final Claims payload = tokenToJws(accessToken).getPayload();
        final Long memberId = payload.get("memberId", Long.class);
        return new LoginMemberInfo(memberId);
    }

    private Jws<Claims> tokenToJws(final String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (final IllegalArgumentException | MalformedJwtException e) {
            System.out.println("111");
            throw new JwtInvalidFormException(token);
        } catch (final WeakKeyException e) {
            System.out.println("222");
            throw new JwtInvalidSecretKeyException(token);
        } catch (final ExpiredJwtException e) {
            System.out.println(e.getMessage());
            throw new JwtInvalidExpiredException();
        }

    }
}
