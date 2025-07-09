package com.calculation.tipcalculation.calculation

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt


class CalculationUseCase {

    fun calculateSigma(values: List<Double>, srznach: Double): Double {
        val count = values.size
        return if (count > 1) {
            values.sumOf { (it - srznach).pow(2) } / (count * (count - 1))
        } else 0.0
    }

    fun calculateSKO(speeds: List<Double>): Double {
        val n = speeds.size
        if (n > 1) {
            val mean = speeds.average()
            val variance = speeds.sumOf { (it - mean).pow(2) } / (n - 1)
            return if (variance == 0.0) 0.0 else sqrt(variance)
        }
        return 0.0
    }

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

    fun calculateDiametersStuff(
        diameters: List<Double>,
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        preomValue: Double,
        srznach: Double,
        averageValue: Double
    ): DiametersResult {
        val closest = diameters.minByOrNull { abs(it - averageValue) } ?: 0.0
        val closestIndex = diameters.indexOf(closest)

        val checkedList = mutableListOf<Pair<Double, Double>>()
        val suitableList = mutableListOf<Double>()
        val unsuitableList = mutableListOf<Double>()

        val checkedDiameters = mutableSetOf<Double>()
        for (step in diameters.indices) {
            val plusIndex = closestIndex + step
            val minusIndex = closestIndex - step

            if (plusIndex in diameters.indices) {
                checkDiameter(
                    diameters[plusIndex],
                    checkedDiameters,
                    patmValue,
                    plsrValue,
                    tsrValue,
                    taspValue,
                    preomValue,
                    srznach,
                    checkedList,
                    suitableList,
                    unsuitableList
                )
            }
            if (minusIndex in diameters.indices && minusIndex != plusIndex) {
                checkDiameter(
                    diameters[minusIndex],
                    checkedDiameters,
                    patmValue,
                    plsrValue,
                    tsrValue,
                    taspValue,
                    preomValue,
                    srznach,
                    checkedList,
                    suitableList,
                    unsuitableList
                )
            }
        }

        val firstSuitable = suitableList
            .filter { it <= 20 }
            .minByOrNull { abs(it - averageValue) }
            ?: 0.0

        val selectedVp = calculateVp(firstSuitable, patmValue, plsrValue, tsrValue, taspValue, preomValue, srznach)

        return DiametersResult(
            closestDiameter = closest,
            firstSuitableDiameter = firstSuitable,
            suitableDiameters = suitableList,
            unsuitableDiameters = unsuitableList,
            checkedDiametersList = checkedList,
            selectedVp = selectedVp
        )
    }


    private fun checkDiameter(
        diameter: Double,
        checkedDiameters: MutableSet<Double>,
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        preomValue: Double,
        srznach: Double,
        checkedList: MutableList<Pair<Double, Double>>,
        suitableList: MutableList<Double>,
        unsuitableList: MutableList<Double>
    ) {
        if (diameter !in checkedDiameters) {
            val vp = calculateVp(diameter, patmValue, plsrValue, tsrValue, taspValue, preomValue, srznach)
            checkedList.add(diameter to vp)

            if (vp <= 20) {
                suitableList.add(diameter)
            } else {
                unsuitableList.add(diameter)
            }
            checkedDiameters.add(diameter)
        }
    }

    fun calculateOtherValues(
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        srznach: Double,
        averageValue: Double
    ): OtherResult {

        val calculatedTip = when {
            averageValue > 5.05 -> 5.3
            averageValue > 4.55 -> 4.8
            averageValue > 4.25 -> 4.3
            averageValue > 4.1 -> 4.2
            else -> 4.0
        }

        val tipSize = when {
            averageValue > 12 -> 13.0
            averageValue > 10.7 -> 11.0
            averageValue > 10.35 -> 10.4
            averageValue > 9.35 -> 10.3
            averageValue > 7.35 -> 8.4
            averageValue > 6.15 -> 6.3
            averageValue > 5.95 -> 6.0
            averageValue > 5.6 -> 5.9
            else -> calculatedTip
        }

        val aspUsl = if (srznach > 0) {
            val tmp = 0.00245 * tipSize.pow(2) * srznach *
                    (patmValue * 133.3 + plsrValue * 9.807) / (273 + tsrValue) *
                    sqrt((273 + taspValue) / (patmValue * 133.3 + plsrValue * 9.807))
            when {
                tmp >= 20 -> 20.0
                tmp > 1 -> round(tmp)
                else -> round(tmp * 10) / 10.0
            }
        } else 0.0

        val paspmm = if (srznach > 0) round((0.327 * aspUsl.pow(2) + 2.35 * aspUsl + 5.951) * 10) / 10.0 else 0.0
        val resultValue = if (srznach > 0) round((plsrValue - paspmm) * 10) / 10.0 else 0.0

        val aspUsl1 = if (srznach > 0) {
            val tmp = 0.00245 * tipSize.pow(2) * srznach *
                    (patmValue * 133.3 + plsrValue * 9.807) / (273 + tsrValue) *
                    sqrt((273 + taspValue) / (patmValue * 133.3 + resultValue * 9.807))
            when {
                tmp >= 20 -> 20.0
                tmp > 1 -> round(tmp)
                else -> round(tmp * 10) / 10.0
            }
        } else 0.0

        val duslov1 = if (srznach > 0) {
            sqrt(
                aspUsl1 / 0.00245 / srznach / (patmValue * 133.3 + plsrValue * 9.807) *
                        (273 + tsrValue) /
                        sqrt((273 + taspValue) / (patmValue * 133.3 + resultValue * 9.807))
            )
        } else 0.0

        val vibrNak = when {
            duslov1 > 5.05 -> 5.3
            duslov1 > 4.55 -> 4.8
            duslov1 > 4.25 -> 4.3
            duslov1 > 4.1 -> 4.2
            else -> 4.0
        }

        val dreal = when {
            duslov1 > 12 -> 13.0
            duslov1 > 10.7 -> 11.0
            duslov1 > 10.35 -> 10.4
            duslov1 > 9.35 -> 10.3
            duslov1 > 7.35 -> 8.4
            duslov1 > 6.15 -> 6.3
            duslov1 > 5.95 -> 6.0
            duslov1 > 5.6 -> 5.9
            else -> vibrNak
        }

        val vsp2 = if (srznach > 0) {
            val tmp = 0.00245 * dreal.pow(2) * srznach *
                    (patmValue * 133.3 + plsrValue * 9.807) / (273.2 + tsrValue) *
                    sqrt((273.2 + taspValue) / (patmValue * 133.3 + resultValue * 9.807))
            when {
                tmp >= 20 -> 20.0
                tmp > 1 -> round(tmp)
                else -> round(tmp * 10) / 10.0
            }
        } else 0.0

        return OtherResult(
            calculatedTip = calculatedTip,
            tipSize = tipSize,
            aspUsl = aspUsl,
            resultValue = resultValue,
            aspUsl1 = aspUsl1,
            duslov1 = duslov1,
            vibrNak = vibrNak,
            dreal = dreal,
            vsp2 = vsp2
        )
    }

    data class DiametersResult(
        val closestDiameter: Double,
        val firstSuitableDiameter: Double,
        val suitableDiameters: List<Double>,
        val unsuitableDiameters: List<Double>,
        val checkedDiametersList: List<Pair<Double, Double>>,
        val selectedVp: Double
    )

    data class OtherResult(
        val calculatedTip: Double,
        val tipSize: Double,
        val aspUsl: Double,
        val resultValue: Double,
        val aspUsl1: Double,
        val duslov1: Double,
        val vibrNak: Double,
        val dreal: Double,
        val vsp2: Double
    )
}
