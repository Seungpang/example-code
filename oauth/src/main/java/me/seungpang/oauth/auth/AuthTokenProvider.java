package me.seungpang.oauth.auth;

import java.time.ZonedDateTime;

public interface AuthTokenProvider {

    String createAccessToken(Long memberId, ZonedDateTime accessTokenExpire);

    LoginMemberInfo getLoginMemberByAccessToken(String accessToken);
}
