package me.seungpang.distributedlock.service;

import lombok.RequiredArgsConstructor;
import me.seungpang.distributedlock.domain.Message;
import me.seungpang.distributedlock.domain.MessageLike;
import me.seungpang.distributedlock.domain.MessageLikeRepository;
import me.seungpang.distributedlock.domain.MessageRepository;
import me.seungpang.distributedlock.service.dto.MessageLikeResponseDto;
import me.seungpang.distributedlock.support.DistributedLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageLikeService {

    private final MessageRepository messageRepository;
    private final MessageLikeRepository messageLikeRepository;

    @DistributedLock(key = "#messageId")
    @Transactional
    public MessageLikeResponseDto likeMessage(Long memberId, Long messageId) {
        final Message message = messageRepository.findById(messageId)
                .orElseThrow(IllegalArgumentException::new);
        message.like();
        messageLikeRepository.save(new MessageLike(memberId, messageId));
        return new MessageLikeResponseDto(message.getLikes(), true);
    }
}
