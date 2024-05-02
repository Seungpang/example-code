package me.serungpang.retryexample.service;

import lombok.RequiredArgsConstructor;
import me.serungpang.retryexample.domain.Message;
import me.serungpang.retryexample.domain.MessageLike;
import me.serungpang.retryexample.domain.MessageLikeRepository;
import me.serungpang.retryexample.domain.MessageRepository;
import me.serungpang.retryexample.service.dto.MessageLikeResponseDto;
import me.serungpang.retryexample.support.RetryOnOptimisticLockingFailure;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MessageLikeService {

    private final MessageRepository messageRepository;
    private final MessageLikeRepository messageLikeRepository;

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    @Transactional
    public MessageLikeResponseDto likeMessageWithRetry(Long memberId, Long messageId) {
        final Message message = messageRepository.findByIdForUpdate(messageId)
                .orElseThrow(IllegalArgumentException::new);
        message.like();
        messageLikeRepository.save(new MessageLike(memberId, messageId));
        return new MessageLikeResponseDto(message.getLikes(), true);
    }
}
