package com.xy.poi.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.xy.poi.validation.BeanValidator
import javax.persistence.Embeddable
import javax.validation.constraints.PositiveOrZero

@Embeddable
data class GeoPoint
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    constructor(@field:PositiveOrZero @field:JsonValue val value: Int) {

    init {
        BeanValidator.validate(this)
    }

    object Attributes {
        const val VALUE = "value"
    }
}