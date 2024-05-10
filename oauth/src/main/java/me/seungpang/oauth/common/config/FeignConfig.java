package me.seungpang.oauth.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "me.seungpang.oauth")
@Configuration
public class FeignConfig {

}
