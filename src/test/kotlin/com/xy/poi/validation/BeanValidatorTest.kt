package com.xy.poi.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.validation.constraints.PositiveOrZero

internal class BeanValidatorTest {

    class Example(@field:PositiveOrZero val value: Int)

    @Test
    fun validateShouldPass() {
        BeanValidator.validate(Example(0))
    }

    @Test
    fun validateShouldThrow() {
        Assertions.assertThrows(java.lang.IllegalStateException::class.java) {
            BeanValidator.validate(Example(-1))
        }
    }

    @Test
    fun getViolationsShouldReturnEmpty() {
        Assertions.assertEquals(
            0,
            BeanValidator.getViolations(Example(0)).size
        )
    }

    @Test
    fun getViolationsShouldReturnOne() {
        Assertions.assertEquals(
            1,
            BeanValidator.getViolations(Example(-1)).size
        )
    }
}