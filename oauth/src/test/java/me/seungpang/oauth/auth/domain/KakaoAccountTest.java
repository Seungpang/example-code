package me.seungpang.oauth.auth.domain;

import me.seungpang.oauth.member.Member;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class KakaoAccountTest {

    @Test
    void 카카오로_계정을_생성한다() {
        final String nickname = "닉네임";
        final LocalDate birthDate = LocalDate.of(2024, 02, 9);
        final LocalDate anniversary = LocalDate.of(2024, 02, 10);
        final Member member = Member.builder()
                .nickname(nickname)
                .birthDate(birthDate)
                .anniversary(anniversary)
                .build();

        final KakaoAccount kakaoAccount = new KakaoAccount(1L, member, false);

        assertAll(
                () -> assertThat(kakaoAccount.getKakaoId()).isEqualTo(1L),
                () -> assertThat(kakaoAccount.isSignUp()).isFalse(),
                () -> assertThat(kakaoAccount.getMember().getNickname()).isEqualTo(nickname),
                () -> assertThat(kakaoAccount.getMember().getBirthDate()).isEqualTo(birthDate),
                () -> assertThat(kakaoAccount.getMember().getAnniversary()).isEqualTo(anniversary),
                () -> assertThat(kakaoAccount.getIsDeleted()).isFalse()
        );
    }

    @Test
    void 카카오_계정을_삭제한다() {
        final Member member = Member.builder()
                .nickname("닉네임")
                .birthDate(LocalDate.of(2024, 02, 9))
                .anniversary(LocalDate.of(2024, 02, 10))
                .build();

        final KakaoAccount kakaoAccount = new KakaoAccount(1L, member, false);

        kakaoAccount.delete();

        assertThat(kakaoAccount.getIsDeleted()).isTrue();
    }
}
