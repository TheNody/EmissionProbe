package com.calculation.tipcalculation.domain.usecase.common

import kotlin.math.pow
import kotlin.math.sqrt

object CalculationCommon {
    fun calculateAverage(speeds: List<Double>) = speeds.average()

    fun calculateSigma(values: List<Double>, srznach: Double): Double =
        if (values.size > 1) values.sumOf { (it - srznach).pow(2) } / (values.size * (values.size - 1))
        else 0.0

    fun calculateSKO(values: List<Double>): Double =
        if (values.size > 1) {
            val mean = values.average()
            val variance = values.sumOf { (it - mean).pow(2) } / (values.size - 1)
            sqrt(variance)
        } else 0.0

    fun calculateIdealTip(srznach: Double): Double =
        if (srznach > 0) 24 / sqrt(srznach) else 0.0

    fun convertPatmToPa(patm: Double) = patm * 133.32

    fun calculateVp(
        diameter: Double,
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        preomValue: Double,
        srznach: Double
    ): Double {
        if ((273 + tsrValue) == 0.0 ||
            (patmValue - preomValue) == 0.0 ||
            (1.293 * (patmValue - preomValue)) <= 0
        ) {
            return 0.0
        }
        return 0.00245 * diameter.pow(2) * srznach * ((patmValue + plsrValue) / (273 + tsrValue)) *
                sqrt((1.293 * (273 + taspValue)) / (1.293 * (patmValue - preomValue)))
    }
}
