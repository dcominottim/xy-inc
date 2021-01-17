package com.xy.poi.domain

import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.Valid

@Embeddable
data class Location(
    @field:AttributeOverride(
        name = GeoPoint.Attributes.VALUE,
        column = Column(name = Attributes.X)
    )
    @field:Valid
    val x: GeoPoint,

    @field:AttributeOverride(
        name = GeoPoint.Attributes.VALUE,
        column = Column(name = Attributes.Y)
    )
    @field:Valid
    val y: GeoPoint
) {

    object Attributes {
        const val X = "x"
        const val Y = "y"
    }
}