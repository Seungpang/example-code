package me.seungpang.oauth.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class AccessTokenCreateInfo {

    private final String accessToken;
    private final ZonedDateTime expire;
}
