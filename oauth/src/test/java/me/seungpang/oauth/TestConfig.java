package me.seungpang.oauth;

import me.seungpang.oauth.auth.KakaoAuthApiFeignClient;
import me.seungpang.oauth.auth.KakaoResourceApiFeignClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public KakaoAuthApiFeignClient kakaoAuthApiFeignClient() {
        return new FakeKakaoAuthApiFeignClient();
    }

    @Bean
    @Primary
    public KakaoResourceApiFeignClient kakaoResourceApiFeignClient() {
        return new FakeKakaoResourceApiFeignClient();
    }
}
