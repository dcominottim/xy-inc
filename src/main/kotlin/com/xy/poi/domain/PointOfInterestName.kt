package com.xy.poi.domain

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class PointOfInterestName(@field:NotBlank val value: String) {

    object Attributes {
        const val VALUE = "value"
    }
}