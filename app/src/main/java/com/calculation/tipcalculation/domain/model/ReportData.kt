package com.calculation.tipcalculation.domain.model

data class ReportData(
    val id: Int = 0,
    val title: String,
    val patm: Double,
    val tsr: Double,
    val tasp: Double,
    val plsr: Double,
    val measurementCount: Int,
    val averageSpeed: Double,
    val calculatedTip: Double,
    val firstSuitableTip: Double,
    val sko: Double
)
