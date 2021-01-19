package com.xy.poi.domain

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class LocationTest {

    @Test
    fun instantiationShouldPass() {
        Location(
            x = GeoPoint(0),
            y = GeoPoint(1)
        )
    }

    @Test
    fun instantiationShouldFail() {
        assertThrows(IllegalStateException::class.java) {
            Location(
                x = GeoPoint(-1),
                y = GeoPoint(1)
            )
        }
    }
}