package com.xy.poi.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.xy.poi.validation.BeanValidator
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class PointOfInterestName
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    constructor(@field:NotBlank @field:JsonValue val value: String) {

    init {
        BeanValidator.validate(this)
    }

    object Attributes {
        const val VALUE = "value"
    }
}