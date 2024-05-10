package me.seungpang.oauth.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import me.seungpang.oauth.auth.JwtAuthTokenProvider;
import me.seungpang.oauth.auth.LoginMemberInfo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AccessTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(LoginMemberDto.class)
                && parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String accessToken = getAccessToken(httpServletRequest.getHeader("authorization"));

        final LoginMemberInfo loginMemberInfo = jwtAuthTokenProvider.getLoginMemberByAccessToken(accessToken);
        final Long memberId = loginMemberInfo.getMemberId();

        return new LoginMemberDto(memberId);
    }

    private String getAccessToken(String accessTokenHeader) {
        if (accessTokenHeader == null) {
            throw new IllegalArgumentException();
        }
        return accessTokenHeader.split(" ")[1];
    }
}
