package me.seungpang.distributedlock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_like_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "message_id")
    private Long messageId;

    public MessageLike(final Long memberId, final Long messageId) {
        this.memberId = memberId;
        this.messageId = messageId;
    }
}
