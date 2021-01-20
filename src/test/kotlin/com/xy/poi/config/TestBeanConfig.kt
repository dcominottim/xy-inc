package com.xy.poi.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer

@TestConfiguration
class TestBeanConfig {

    @Bean
    fun hypermediaRestTemplate(configurer: HypermediaRestTemplateConfigurer): RestTemplateCustomizer {
        return RestTemplateCustomizer { restTemplate -> configurer.registerHypermediaTypes(restTemplate)}
    }
}