package com.calculation.tipcalculation.domain.model

data class CalculationData(
    val patm: Double = 0.0,
    val srznach: Double = 0.0,
    val sigma: Double = 0.0,
    val sko: Double = 0.0,
    val average: Double = 0.0,

    val closestDiameter: Double = 0.0,
    val firstSuitableDiameter: Double = 0.0,
    val suitableDiameters: List<Double> = emptyList(),
    val unsuitableDiameters: List<Double> = emptyList(),
    val checkedDiametersList: List<Pair<Double, Double>> = emptyList(),
    val selectedVp: Double = 0.0,

    val calculatedTip: Double = 0.0,
    val tipSize: Double = 0.0,
    val aspUsl: Double = 0.0,
    val result: Double = 0.0,
    val aspUsl1: Double = 0.0,
    val duslov1: Double = 0.0,
    val vibrNak: Double = 0.0,
    val dreal: Double = 0.0,
    val vsp2: Double = 0.0
)
