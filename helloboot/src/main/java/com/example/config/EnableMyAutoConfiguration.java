package com.example.config;

import com.example.config.autoconfig.DispatcherServiceConfig;
import com.example.config.autoconfig.TomcatWebServerConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({DispatcherServiceConfig.class, TomcatWebServerConfig.class})
public @interface EnableMyAutoConfiguration {
}
