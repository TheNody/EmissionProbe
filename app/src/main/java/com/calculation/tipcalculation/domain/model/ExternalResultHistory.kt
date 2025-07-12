package com.calculation.tipcalculation.domain.model

data class ExternalResultHistory(
    val id: Int = 0,
    val patm: Double,
    val plsr: Double,
    val preom: Double,
    val tsr: Double,
    val tasp: Double,
    val speeds: List<Double>,
    val srznach: Double,
    val sigma: Double,
    val sko: Double,
    val average: Double,
    val calculatedTip: Double,
    val tipSize: Double,
    val aspUsl: Double,
    val result: Double,
    val aspUsl1: Double,
    val duslov1: Double,
    val vibrNak: Double,
    val dreal: Double,
    val vsp2: Double,
    val closestDiameter: Double,
    val firstSuitableDiameter: Double,
    val selectedVp: Double,
    val checkedDiametersList: List<Pair<Double, Double>>,
    val timestamp: String
)