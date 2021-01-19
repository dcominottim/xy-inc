package com.xy.poi.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PointOfInterestNameTest {

    @Test
    fun instantiationShouldPass() {
        PointOfInterestName("Bar")
    }

    @Test
    fun instantiationShouldFailWhenEmpty() {
        assertThrows(IllegalStateException::class.java) {
            PointOfInterestName("")
        }
    }

    @Test
    fun instantiationShouldFailWhenBlank() {
        assertThrows(IllegalStateException::class.java) {
            PointOfInterestName(" ")
        }
    }
}