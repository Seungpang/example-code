package me.seungpang.distributedlock.service;

import me.seungpang.distributedlock.DatabaseCleaner;
import me.seungpang.distributedlock.domain.Member;
import me.seungpang.distributedlock.domain.MemberRepository;
import me.seungpang.distributedlock.domain.Message;
import me.seungpang.distributedlock.domain.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MessageLikeServiceTest {

    private static final Integer parallelism = 5;
    private final ExecutorService executorService = Executors.newFixedThreadPool(parallelism);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MessageLikeService messageLikeService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.afterPropertiesSet();
        databaseCleaner.execute();
        final Member member = memberRepository.save(new Member("abc"));
        messageRepository.save(new Message("aaaa", member, 0L));
        for (int i = 0; i < 1000; i++) {
            memberRepository.save(new Member("seungapng" + i));
        }
    }

    @Test
    void 좋아요_정합성_테스트() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            long userId = i + 1;
            executorService.submit(() -> {
                try {
                    messageLikeService.likeMessage(userId, 1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        assertThat(messageRepository.findById(1L).get().getLikes()).isEqualTo(100L);
    }
}
