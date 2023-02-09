package com.example.config.autoconfig;

import com.example.config.MyAutoConfiguration;
import com.example.config.MyConfigurationProperties;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
public class PropertyPostProcessorConfig {
    @Bean
    BeanPostProcessor propertyPostProcessor(Environment env) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(final Object bean, final String beanName)
                    throws BeansException {
                final MyConfigurationProperties annotation = AnnotationUtils.findAnnotation(bean.getClass(),
                        MyConfigurationProperties.class);
                if (annotation == null) {
                    return bean;
                }

                final Map<String, Object> attrs = AnnotationUtils.getAnnotationAttributes(annotation);
                final String prefix = (String) attrs.get("prefix");

                return Binder.get(env).bindOrCreate(prefix, bean.getClass());
            }
        };
    }
}
