package com.calculation.tipcalculation.domain.usecase.external.external_calc

import com.calculation.tipcalculation.domain.model.DiameterAnalysisResult
import com.calculation.tipcalculation.domain.model.ExternalResultData
import com.calculation.tipcalculation.domain.model.OtherResult
import com.calculation.tipcalculation.domain.repository.ExternalFilterTipRepository
import com.calculation.tipcalculation.domain.usecase.common.CalculationCommon
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class ExternalCalculationUseCase @Inject constructor(
    private val filterRepository: ExternalFilterTipRepository
) {

    suspend fun calculateExternal(
        speeds: List<Double>,
        patm: Double,
        plsr: Double,
        tsr: Double,
        tasp: Double,
        preom: Double
    ): ExternalResultData {
        val filterTips = filterRepository.getAllSync()
        val diameters = filterTips.map { it.value }

        val srznach = CalculationCommon.calculateAverage(speeds)
        val sigma = CalculationCommon.calculateSigma(speeds, srznach)
        val sko = CalculationCommon.calculateSKO(speeds)
        val averageValue = CalculationCommon.calculateIdealTip(srznach)

        val (closestDiameter, firstSuitableDiameter, suitableList, unsuitableList, checkedVpList) =
            calculateDiametersStuff(diameters, patm, plsr, tsr, tasp, preom, srznach, averageValue)

        val other = calculateOtherValues(patm, plsr, tsr, tasp, srznach, averageValue)

        return ExternalResultData(
            patm,
            tsr,
            tasp,
            plsr,
            preom,
            speeds,
            srznach,
            sigma,
            sko,
            averageValue,
            other.calculatedTip,
            other.tipSize,
            other.aspUsl,
            other.resultValue,
            other.aspUsl1,
            other.duslov1,
            other.vibrNak,
            other.dreal,
            other.vsp2,
            closestDiameter,
            firstSuitableDiameter,
            suitableList,
            unsuitableList,
            checkedVpList,
            checkedVpList.firstOrNull { it.first == firstSuitableDiameter }?.second ?: 0.0
        )
    }

    private fun calculateDiametersStuff(
        diameters: List<Double>,
        patm: Double,
        plsr: Double,
        tsr: Double,
        tasp: Double,
        preom: Double,
        srznach: Double,
        average: Double
    ): DiameterAnalysisResult {
        val closest = diameters.minByOrNull { abs(it - average) } ?: 0.0
        val closestIndex = diameters.indexOf(closest)

        val checked = mutableListOf<Pair<Double, Double>>()
        val suitable = mutableListOf<Double>()
        val unsuitable = mutableListOf<Double>()
        val visited = mutableSetOf<Double>()

        for (step in diameters.indices) {
            val plus = closestIndex + step
            val minus = closestIndex - step

            listOfNotNull(
                diameters.getOrNull(plus)?.takeIf { it !in visited },
                diameters.getOrNull(minus)?.takeIf { it !in visited && it != diameters.getOrNull(plus) }
            ).forEach { diameter ->
                val vp = CalculationCommon.calculateVp(diameter, patm, plsr, tsr, tasp, preom, srznach)
                checked.add(diameter to vp)
                visited.add(diameter)
                if (vp <= 20) suitable.add(diameter) else unsuitable.add(diameter)
            }
        }

        val firstSuitable = suitable.filter { it <= 20 }.minByOrNull { abs(it - average) } ?: 0.0
        return DiameterAnalysisResult(closest, firstSuitable, suitable, unsuitable, checked)
    }

    private fun calculateOtherValues(
        patm: Double,
        plsr: Double,
        tsr: Double,
        tasp: Double,
        srznach: Double,
        average: Double
    ): OtherResult {

        val calculatedTip = when {
            average > 5.05 -> 5.3
            average > 4.55 -> 4.8
            average > 4.25 -> 4.3
            average > 4.1 -> 4.2
            else -> 4.0
        }

        val tipSize = when {
            average > 12 -> 13.0
            average > 10.7 -> 11.0
            average > 10.35 -> 10.4
            average > 9.35 -> 10.3
            average > 7.35 -> 8.4
            average > 6.15 -> 6.3
            average > 5.95 -> 6.0
            average > 5.6 -> 5.9
            else -> calculatedTip
        }

        val aspUsl = if (srznach > 0) {
            val tmp = 0.00245 * tipSize.pow(2) * srznach *
                    (patm + plsr) / (273 + tsr) *
                    sqrt((273 + tasp) / (patm + plsr))
            formatAsp(tmp)
        } else 0.0

        val paspmm = if (srznach > 0)
            round((0.327 * aspUsl.pow(2) + 2.35 * aspUsl + 5.951) * 10) / 10.0
        else 0.0

        val resultValue = if (srznach > 0)
            round((plsr - paspmm) * 10) / 10.0
        else 0.0

        val aspUsl1 = if (srznach > 0) {
            val tmp = 0.00245 * tipSize.pow(2) * srznach *
                    (patm + plsr) / (273 + tsr) *
                    sqrt((273 + tasp) / (patm + resultValue))
            formatAsp(tmp)
        } else 0.0

        val duslov1 = if (srznach > 0) {
            sqrt(
                aspUsl1 / 0.00245 / srznach / (patm + plsr) *
                        (273 + tsr) /
                        sqrt((273 + tasp) / (patm + resultValue))
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
                    (patm + plsr) / (273.2 + tsr) *
                    sqrt((273.2 + tasp) / (patm + resultValue))
            formatAsp(tmp)
        } else 0.0

        return OtherResult(
            calculatedTip, tipSize, aspUsl, resultValue, aspUsl1, duslov1, vibrNak, dreal, vsp2
        )
    }

    private fun formatAsp(tmp: Double): Double {
        return when {
            tmp >= 20 -> 20.0
            tmp > 1 -> round(tmp)
            else -> round(tmp * 10) / 10.0
        }
    }
}