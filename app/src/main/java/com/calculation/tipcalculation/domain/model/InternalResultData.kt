package com.calculation.tipcalculation.domain.model

data class InternalResultData(
    val patm: Double,
    val tsr: Double,
    val tasp: Double,
    val plsr: Double,
    val preom: Double,
    val speeds: List<Double>,
    val averageSpeed: Double,
    val averageTip: Double,
    val selectedTip: Double,
    val vp: Double
)