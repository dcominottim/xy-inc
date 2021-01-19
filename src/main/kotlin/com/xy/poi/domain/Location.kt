package com.xy.poi.domain

import com.xy.poi.validation.BeanValidator
import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.validation.Valid

@Embeddable
data class Location(
    @field:Embedded
    @field:AttributeOverride(
        name = GeoPoint.Attributes.VALUE,
        column = Column(name = Attributes.X, nullable = false)
    )
    @field:Valid
    val x: GeoPoint,

    @field:Embedded
    @field:AttributeOverride(
        name = GeoPoint.Attributes.VALUE,
        column = Column(name = Attributes.Y, nullable = false)
    )
    @field:Valid
    val y: GeoPoint
) {
    init {
        BeanValidator.validate(this)
    }

    object Attributes {
        const val X = "x"
        const val Y = "y"
    }
}