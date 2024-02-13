package me.seungpang.oauth.member;

import me.seungpang.oauth.member.exception.TooLongNicknameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @ParameterizedTest
    @ValueSource(strings = {"닉네임", "abcabcabcabcabcabcab"})
    void 닉네임을_생선한다(final String value) {
        final Nickname nickname = Nickname.create(value);

        assertThat(nickname.getValue()).isEqualTo(value);
    }

    @Test
    void 닉네임이_20자를_초과하면_예외_발생() {
        assertThatThrownBy(() -> Nickname.create("a".repeat(21)))
                .isInstanceOf(TooLongNicknameException.class);
    }
}
