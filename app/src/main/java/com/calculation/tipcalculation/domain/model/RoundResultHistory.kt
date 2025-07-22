package com.calculation.tipcalculation.domain.model

import com.calculation.tipcalculation.utils.MeasurementRule

data class RoundResultHistory(
    val id: Int = 0,
    val d: Double,
    val de: Double,
    val l: Double,
    val lOverDe: Double,
    val lz: Double,
    val rule: MeasurementRule,
    val ki: List<Double>?,
    val timestamp: String
)
