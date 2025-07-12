package com.calculation.tipcalculation.domain.model

data class DiameterAnalysisResult(
    val closestDiameter: Double,
    val firstSuitableDiameter: Double,
    val suitableDiameters: List<Double>,
    val unsuitableDiameters: List<Double>,
    val checkedVpList: List<Pair<Double, Double>>
)