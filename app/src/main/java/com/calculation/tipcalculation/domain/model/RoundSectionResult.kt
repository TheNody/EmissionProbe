package com.calculation.tipcalculation.domain.model

import com.calculation.tipcalculation.utils.MeasurementRule
import com.calculation.tipcalculation.utils.KiCoefficients

data class RoundSectionResult(
    val d: Double,
    val de: Double,
    val lOverDe: Double,
    val rule: MeasurementRule,
    val ki: KiCoefficients?
)
