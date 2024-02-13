package me.seungpang.oauth.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungpang.oauth.member.exception.TooLongNicknameException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    public static final int MAX_LENGTH = 20;

    @Column(name = "nickname")
    private String value;

    private Nickname(final String value) {
        this.value = value;
    }

    public static Nickname create(final String value) {
        validate(value);
        return new Nickname(value);
    }

    private static void validate(final String value) {
        if (isOverLength(value)) {
            throw new TooLongNicknameException(value);
        }
    }

    private static boolean isOverLength(final String value) {
        return value.length() > MAX_LENGTH;
    }
}
