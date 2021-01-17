package com.xy.poi.config

import com.xy.poi.domain.converter.LocationInputConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.support.ConfigurableConversionService

import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.validation.Validator

@Configuration
class RestRepositoryConfig(
    @Autowired
    @Qualifier("defaultValidator")
    val validator: Validator,
    @Autowired
    val locationInputConverter: LocationInputConverter
): RepositoryRestConfigurer {

    override fun configureValidatingRepositoryEventListener(validatingListener: ValidatingRepositoryEventListener) {
        validatingListener.addValidator("beforeCreate", validator)
        validatingListener.addValidator("beforeSave", validator)
    }

    override fun configureConversionService(conversionService: ConfigurableConversionService) {
        conversionService.addConverter(locationInputConverter)
    }
}