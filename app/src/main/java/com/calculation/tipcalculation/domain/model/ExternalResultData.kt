package com.calculation.tipcalculation.domain.model

data class ExternalResultData(
    val patm: Double,
    val tsr: Double,
    val tasp: Double,
    val plsr: Double,
    val preom: Double,
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
    val suitableDiameters: List<Double>,
    val unsuitableDiameters: List<Double>,
    val checkedDiametersList: List<Pair<Double, Double>>,
    val selectedVp: Double
)