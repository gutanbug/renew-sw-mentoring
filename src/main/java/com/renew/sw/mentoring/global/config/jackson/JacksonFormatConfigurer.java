package com.renew.sw.mentoring.global.config.jackson;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public interface JacksonFormatConfigurer {
    void configure(Jackson2ObjectMapperBuilder builder);
}
