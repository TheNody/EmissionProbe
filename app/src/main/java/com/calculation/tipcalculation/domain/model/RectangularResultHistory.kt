package com.calculation.tipcalculation.domain.model

import com.calculation.tipcalculation.utils.MeasurementPointRule

data class RectangularResultHistory(
    val id: Int = 0,
    val a: Double,
    val b: Double,
    val l: Double,
    val de: Double,
    val lOverDe: Double,
    val lz: Double,
    val rule: MeasurementPointRule,
    val ki: List<Double>?,
    val timestamp: String
)