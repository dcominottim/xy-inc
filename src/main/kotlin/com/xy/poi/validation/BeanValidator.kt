package com.xy.poi.validation

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

object BeanValidator {

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    fun <T: Any> validate(validationTarget: T) {
        val violations: Set<ConstraintViolation<T>> = validator.validate(validationTarget)
        check(violations.isEmpty()) { "Object has state violations" }
    }

    fun <T: Any> getViolations(validationTarget: T): Set<ConstraintViolation<T>> {
        return validator.validate(validationTarget)
    }
}