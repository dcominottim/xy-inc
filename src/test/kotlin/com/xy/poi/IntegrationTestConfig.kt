package com.xy.poi

import com.xy.poi.config.SpringProfiles
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate

@ActiveProfiles(value = [SpringProfiles.TESTING])
open class IntegrationTestConfig {

    @Bean
    fun hypermediaRestTemplate(configurer: HypermediaRestTemplateConfigurer): RestTemplateCustomizer {
        return RestTemplateCustomizer { restTemplate -> configurer.registerHypermediaTypes(restTemplate)}
    }
}