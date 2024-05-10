package me.seungpang.oauth.auth.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seungpang.oauth.auth.KakaoLoginRequest;
import me.seungpang.oauth.auth.LoginResultResponse;
import me.seungpang.oauth.auth.application.KakaoLoginService;
import me.seungpang.oauth.auth.application.dto.KakaoLoginResult;
import me.seungpang.oauth.common.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthV1Controller {

    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/kakao/login")
    public ResponseEntity<CommonResponse<LoginResultResponse>> kakaoLogin(
            @RequestBody final KakaoLoginRequest request
    ) {
        final KakaoLoginResult loginResult = kakaoLoginService.login(
                request.getAuthorizationCode(),
                request.getRedirectUri()
        );
        log.info("성공함???");
        return CommonResponse.success(LoginResultResponse.from(loginResult));
    }
}
