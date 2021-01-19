package com.xy.poi.domain

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

internal class PointOfInterestTest {

    @Test
    fun instantiationShouldPass() {
        PointOfInterest(
            id = UUID.randomUUID(),
            name = PointOfInterestName("Bar"),
            location = Location(
                x = GeoPoint(0),
                y = GeoPoint(1)
            )
        )
    }

    @Test
    fun instantiationShouldFail() {
        assertThrows(IllegalStateException::class.java) {
            PointOfInterest(
                id = UUID.randomUUID(),
                name = PointOfInterestName("Bar"),
                location = Location(
                    x = GeoPoint(-1),
                    y = GeoPoint(1)
                )
            )
        }
    }
}