package me.seungpang.oauth.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMemberDto {

    private Long memberId;

    public LoginMemberDto(final Long memberId) {
        this.memberId = memberId;
    }
}
