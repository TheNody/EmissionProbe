package com.calculation.tipcalculation.domain.model

sealed class ValidationResult {
    object Valid : ValidationResult()
    object MissingSpeeds : ValidationResult()
    object MissingTips : ValidationResult()
}