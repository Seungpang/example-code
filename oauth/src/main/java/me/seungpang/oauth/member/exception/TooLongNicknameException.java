package me.seungpang.oauth.member.exception;

import me.seungpang.oauth.common.exception.InvalidParamException;

import static me.seungpang.oauth.member.Nickname.MAX_LENGTH;

public class TooLongNicknameException extends InvalidParamException {
    public TooLongNicknameException(final String nickname) {
        super(String.format("닉네임은 %d자를 초과할 수 없습니다. nickname = %s", MAX_LENGTH, nickname));
    }
}
