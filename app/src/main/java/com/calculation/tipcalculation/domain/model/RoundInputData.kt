package com.calculation.tipcalculation.domain.model

import com.calculation.tipcalculation.utils.MeasurementRule
import com.calculation.tipcalculation.utils.KiCoefficients

data class RoundInputData(
    val d: String,
    val l: String,
    val dDouble: Double? = null,
    val de: Double? = null,
    val lOverDe: Double? = null,
    val lz: Double? = null,
    val rule: MeasurementRule? = null,
    val ki: KiCoefficients? = null
)