package com.calculation.tipcalculation.domain.model

import com.calculation.tipcalculation.utils.KiCoefficients
import com.calculation.tipcalculation.utils.MeasurementPointRule

data class RectangularInputData(
    val a: String,
    val b: String,
    val l: String,
    val de: Double? = null,
    val lOverDe: Double? = null,
    val lz: Double? = null,
    val rule: MeasurementPointRule? = null,
    val ki: KiCoefficients? = null
)