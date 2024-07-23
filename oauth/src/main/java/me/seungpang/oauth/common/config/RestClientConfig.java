package me.seungpang.oauth.common.config;

import me.seungpang.oauth.auth.KakaoApiHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().baseUrl("https://api.kakao.com").build();
    }

    @Bean
    public KakaoApiHttpClient kakaoApiHttpClient(RestClient restClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(KakaoApiHttpClient.class);
    }
}
