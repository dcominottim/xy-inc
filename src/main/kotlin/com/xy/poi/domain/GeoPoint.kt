package com.xy.poi.domain

import javax.persistence.Embeddable
import javax.validation.constraints.PositiveOrZero

@Embeddable
data class GeoPoint(@field:PositiveOrZero val value: Int) {

    object Attributes {
        const val VALUE = "value"
    }
}