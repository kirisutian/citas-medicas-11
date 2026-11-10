package com.christian.commons.configuration;

import com.christian.commons.clients.FeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
