package com.calculation.tipcalculation.domain.model

import com.calculation.tipcalculation.utils.KiCoefficients
import com.calculation.tipcalculation.utils.MeasurementPointRule

data class MeasurementResult(
    val de: Double,
    val lOverDe: Double,
    val rule: MeasurementPointRule?,
    val ki: KiCoefficients?
)