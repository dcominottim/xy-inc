package com.xy.poi.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GeoPointTest {

    @Test
    fun instantiationShouldPassAtLowerBound() {
        GeoPoint(0)
    }

    @Test
    fun instantiationShouldPassAtAboveLowerBound() {
        GeoPoint(1)
    }

    @Test
    fun instantiationShouldFailBelowLowerBound() {
        assertThrows(IllegalStateException::class.java) {
            GeoPoint(-1)
        }
    }
}