package me.seungpang.oauth.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoAuthApiClient", url = "${oauth.kakao.authorizationServerHost}", primary = false)
public interface KakaoAuthApiFeignClient {

    @PostMapping("/oauth/token")
    KakaoAccessTokenResponse getAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String authorizationCode,
            @RequestParam("client_secret") String clientSecret
    );
}
