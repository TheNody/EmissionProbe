package com.calculation.tipcalculation.domain.model

data class ReportData(
    val patm: Double,
    val tsr: Double,
    val tasp: Double,
    val plsr: Double,
    val measurementCount: Int,
    val averageSpeed: Double,
    val calculatedTip: Double,
    val firstSuitableTip: Double,
    val sko: Double,
    val timestamp: String
)