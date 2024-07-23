package me.seungpang.oauth.auth;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface KakaoApiHttpClient {

    @GetExchange("/v2/user/me")
    KakaoUserResponse getKakaoUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestHeader("Content-Type") String contentTypeHeader,
            @RequestParam("secure_resource") Boolean secureResource,
            @RequestParam("property_keys") String propertyKeys
    );

    @PostExchange("/oauth/token")
    KakaoAccessTokenResponse getAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String authorizationCode,
            @RequestParam("client_secret") String clientSecret
    );
}
