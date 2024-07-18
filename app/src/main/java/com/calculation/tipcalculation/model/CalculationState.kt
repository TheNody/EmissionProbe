package com.calculation.tipcalculation.model

data class CalculationState(
    var externalCalculationDone: Boolean = false,
    var internalCalculationDone: Boolean = false
)
