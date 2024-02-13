package me.seungpang.oauth.member;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberTest {

    @Test
    void 멤버를_생성한다() {
        String nickname = "닉네임";
        LocalDate birthDate = LocalDate.of(2024, 02, 9);
        LocalDate anniversary = LocalDate.of(2024, 02, 10);
        final Member member = new Member(nickname, birthDate, anniversary);

        assertAll(
                () -> assertThat(member.getNickname()).isEqualTo(nickname),
                () -> assertThat(member.getBirthDate()).isEqualTo(birthDate),
                () -> assertThat(member.getAnniversary()).isEqualTo(anniversary)
        );
    }
}
