package com.calculation.tipcalculation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf

data class ReportData(
    val patm: Double = 0.0,
    var tsr: MutableState<Double> = mutableDoubleStateOf(0.0),
    var tasp: MutableState<Double> = mutableDoubleStateOf(0.0),
    var plsr: MutableState<Double> = mutableDoubleStateOf(0.0),
    val measurementCount: Int = 0,
    val averageSpeed: Double = 0.0,
    val calculatedTip: Double = 0.0,
    val firstSuitableTip: Double = 0.0,
    val sko: Double = 0.0
)
