package com.example.config;

import java.util.Objects;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

public class MyConfigurationPropertiesImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(final AnnotationMetadata importingClassMetadata) {
        final MultiValueMap<String, Object> attr = importingClassMetadata.getAllAnnotationAttributes(
                EnableMyConfigurationProperties.class.getName());
        final Class propertyClass = (Class) Objects.requireNonNull(attr).getFirst("value");
        return new String[]{
                propertyClass.getName()
        };
    }
}
