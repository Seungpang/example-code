package me.seungpang.oauth.auth;

import lombok.Getter;

@Getter
public class LoginMemberInfo {

    private final Long memberId;

    public LoginMemberInfo(final Long memberId) {
        this.memberId = memberId;
    }
}
