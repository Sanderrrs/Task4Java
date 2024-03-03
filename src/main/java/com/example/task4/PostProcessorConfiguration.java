package com.example.task4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorConfiguration {

    @Bean
    public LogTransform logBeanPostProcessor() {
        return new LogTransform();
    }
}
