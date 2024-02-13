package me.seungpang.oauth.member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungpang.oauth.common.entity.AbstractBaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "member")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update member set is_deleted = true, updated_at = now() where id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Nickname nickname;
    private LocalDate birthDate;
    private LocalDate anniversary;

    @Builder
    public Member(final String nickname, final LocalDate birthDate, final LocalDate anniversary) {
        this.nickname = Nickname.create(nickname);
        this.birthDate = birthDate;
        this.anniversary = anniversary;
    }

    public String getNickname() {
        return nickname.getValue();
    }
}
