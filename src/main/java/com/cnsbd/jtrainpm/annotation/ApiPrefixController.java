package com.cnsbd.jtrainpm.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@RequestMapping("${spring.web.api-v1-prefix}")
public @interface ApiPrefixController {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
