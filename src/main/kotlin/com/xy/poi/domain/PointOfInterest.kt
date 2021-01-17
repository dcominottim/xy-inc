package com.xy.poi.domain

import java.util.UUID
import javax.persistence.*
import javax.validation.Valid

@Entity
@Table(name = PointOfInterest.Table.NAME)
@Access(AccessType.FIELD)
class PointOfInterest(
    id: UUID = UUID.randomUUID(),
    name: PointOfInterestName,
    location: Location
) {
    @field:Id
    var id: UUID = id
        protected set

    @Embedded
    @AttributeOverride(
        name = PointOfInterestName.Attributes.VALUE,
        column = Column(name = "name", unique = true)
    )
    @Valid
    var name: PointOfInterestName = name
        protected set

    @Embedded
    @Valid
    var location: Location = location
        protected set

    object Table {
        const val NAME = "point_of_interest"
    }

    object Rest {
        const val COLLECTION = "pointsOfInterest"
    }
}