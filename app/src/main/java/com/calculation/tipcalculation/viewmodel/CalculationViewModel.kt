package com.calculation.tipcalculation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTip
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class CalculationViewModel : ViewModel() {

    private var diameters: List<Double> = emptyList()
    private var externalDiameters: List<Double> = emptyList()

    fun setFilterTips(filterTips: List<ExternalFilterTip>) {
        diameters = filterTips.map { it.value }
        Log.d("CalculationViewModel", "Получены значения внешних фильтров: $diameters")
    }

    fun setExternalFilterTips(filterTips: List<FilterTip>) {
        externalDiameters = filterTips.map { it.value }
        Log.d("CalculationViewModel", "Получены значения внутренних фильтров: $externalDiameters")
    }

    fun calculateResult(
        patm: Double?,
        speeds: List<Double?>,
        plsr: Double?,
        tsr: Double?,
        tasp: Double?,
        preom: Double?,
        stateViewModel: StateViewModel
    ) {
        stateViewModel.resetValues()

        val nonNullSpeeds = speeds.filterNotNull()
        val (patmValue, plsrValue, tsrValue, taspValue, preomValue) = listOf(
            patm ?: 0.0, plsr ?: 0.0, tsr ?: 0.0, tasp ?: 0.0, preom ?: 0.0
        )

        stateViewModel.srznach = nonNullSpeeds.average()
        stateViewModel.average = if (nonNullSpeeds.isNotEmpty()) 24 / sqrt(stateViewModel.srznach) else 0.0
        stateViewModel.sigma = calculateSigma(nonNullSpeeds, stateViewModel.srznach)

        calculateDiameters(diameters, patmValue, plsrValue, tsrValue, taspValue, preomValue, stateViewModel)
        calculateAspUsl(patmValue, plsrValue, tsrValue, taspValue, stateViewModel)
        calculateResultValue(patmValue, plsrValue, tsrValue, taspValue, stateViewModel)

        Log.d("CalculationViewModel", "P атм в Па: $patmValue")
        Log.d("CalculationViewModel", "Идеальный наконечник: ${stateViewModel.average}")
        Log.d("CalculationViewModel", "Ближайший наконечник: ${stateViewModel.closestDiameter}")
        Log.d("CalculationViewModel", "Самый первый ближайший подходящий наконечник: ${stateViewModel.firstSuitableDiameter}")
        Log.d("CalculationViewModel", "Подходящие наконечники: ${stateViewModel.suitableDiameters.joinToString()}")
        Log.d("CalculationViewModel", "Неподходящие наконечники: ${stateViewModel.unsuitableDiameters.joinToString()}")
    }

    private fun calculateSigma(values: List<Double>, srznach: Double): Double {
        val count = values.size
        return if (count > 1) {
            values.sumOf { (it - srznach).pow(2) } / (count * (count - 1))
        } else 0.0
    }

    private fun calculateDiameters(
        diameters: List<Double>,
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        preomValue: Double,
        stateViewModel: StateViewModel
    ) {
        stateViewModel.suitableDiameters.clear()
        stateViewModel.unsuitableDiameters.clear()
        stateViewModel.checkedDiametersList.clear()

        stateViewModel.closestDiameter = diameters.minByOrNull { abs(it - stateViewModel.average) } ?: 0.0
        val closestIndex = diameters.indexOf(stateViewModel.closestDiameter)
        val checkedDiameters = mutableSetOf<Double>()
        stateViewModel.firstSuitableDiameter = 0.0

        for (step in diameters.indices) {
            checkDiameter(closestIndex + step, checkedDiameters, diameters, patmValue, plsrValue, tsrValue, taspValue, preomValue, stateViewModel)
            checkDiameter(closestIndex - step, checkedDiameters, diameters, patmValue, plsrValue, tsrValue, taspValue, preomValue, stateViewModel)
        }

        calculateTipSize(stateViewModel)
    }

    private fun checkDiameter(
        index: Int,
        checkedDiameters: MutableSet<Double>,
        diameters: List<Double>,
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        preomValue: Double,
        stateViewModel: StateViewModel
    ) {
        if (index in diameters.indices) {
            val currentDiameter = diameters[index]
            if (currentDiameter !in checkedDiameters) {
                val vp = calculateVp(currentDiameter, patmValue, plsrValue, tsrValue, taspValue, preomValue, stateViewModel.srznach)
                stateViewModel.checkedDiametersList.add(currentDiameter to vp)
                if (vp <= 20) {
                    stateViewModel.suitableDiameters.add(currentDiameter)
                    if (stateViewModel.firstSuitableDiameter == 0.0) {
                        stateViewModel.firstSuitableDiameter = currentDiameter
                        stateViewModel.selectedVp = vp
                    }
                } else {
                    stateViewModel.unsuitableDiameters.add(currentDiameter)
                }
                checkedDiameters.add(currentDiameter)
                Log.d("CalculationViewModel", "Проверка диаметра: $currentDiameter, vp: $vp")
            }
        }
    }

    private fun calculateVp(
        diameter: Double,
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        preomValue: Double,
        srznach: Double
    ): Double {
        return if ((273 + tsrValue) != 0.0 && (patmValue * 133.32 - preomValue) != 0.0 && (1.293 * (patmValue * 133.32 - preomValue)) > 0) {
            0.00245 * diameter.pow(2) * srznach * ((patmValue * 133.32 + plsrValue) / (273 + tsrValue)) *
                    sqrt((1.293 * (273 + taspValue)) / (1.293 * (patmValue * 133.32 - preomValue)))
        } else 0.0
    }

    private fun calculateTipSize(stateViewModel: StateViewModel) {
        stateViewModel.calculatedTip = when {
            stateViewModel.average > 5.05 -> 5.3
            stateViewModel.average > 4.55 -> 4.8
            stateViewModel.average > 4.25 -> 4.3
            stateViewModel.average > 4.1 -> 4.2
            else -> 4.0
        }

        stateViewModel.tipSize = when {
            stateViewModel.average > 12 -> 13.0
            stateViewModel.average > 10.7 -> 11.0
            stateViewModel.average > 10.35 -> 10.4
            stateViewModel.average > 9.35 -> 10.3
            stateViewModel.average > 7.35 -> 8.4
            stateViewModel.average > 6.15 -> 6.3
            stateViewModel.average > 5.95 -> 6.0
            stateViewModel.average > 5.6 -> 5.9
            else -> stateViewModel.calculatedTip
        }
    }

    private fun calculateAspUsl(
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        stateViewModel: StateViewModel
    ) {
        stateViewModel.aspUsl = if (stateViewModel.srznach > 0) {
            val value = 0.00245 * stateViewModel.tipSize.pow(2) * stateViewModel.srznach * (patmValue * 133.3 + plsrValue * 9.807) / (273 + tsrValue) *
                    sqrt((273 + taspValue) / (patmValue * 133.3 + plsrValue * 9.807))
            when {
                value >= 20 -> 20.0
                value > 1 -> round(value)
                else -> round(value * 10) / 10.0
            }
        } else 0.0
    }

    private fun calculateResultValue(
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        stateViewModel: StateViewModel
    ) {
        val paspmm = if (stateViewModel.srznach > 0) round((0.327 * stateViewModel.aspUsl.pow(2) + 2.35 * stateViewModel.aspUsl + 5.951) * 10) / 10.0 else 0.0
        stateViewModel.result = if (stateViewModel.srznach > 0) round((plsrValue - paspmm) * 10) / 10.0 else 0.0

        stateViewModel.aspUsl1 = if (stateViewModel.srznach > 0) {
            val value = 0.00245 * stateViewModel.tipSize.pow(2) * stateViewModel.srznach * (patmValue * 133.3 + plsrValue * 9.807) / (273 + tsrValue) *
                    sqrt((273 + taspValue) / (patmValue * 133.3 + stateViewModel.result * 9.807))
            when {
                value >= 20 -> 20.0
                value > 1 -> round(value)
                else -> round(value * 10) / 10.0
            }
        } else 0.0

        stateViewModel.duslov1 = if (stateViewModel.srznach > 0) sqrt(stateViewModel.aspUsl1 / 0.00245 / stateViewModel.srznach / (patmValue * 133.3 + plsrValue * 9.807) * (273 + tsrValue) /
                sqrt((273 + taspValue) / (patmValue * 133.3 + stateViewModel.result * 9.807))) else 0.0

        stateViewModel.vibrNak = when {
            stateViewModel.duslov1 > 5.05 -> 5.3
            stateViewModel.duslov1 > 4.55 -> 4.8
            stateViewModel.duslov1 > 4.25 -> 4.3
            stateViewModel.duslov1 > 4.1 -> 4.2
            else -> 4.0
        }

        stateViewModel.dreal = when {
            stateViewModel.duslov1 > 12 -> 13.0
            stateViewModel.duslov1 > 10.7 -> 11.0
            stateViewModel.duslov1 > 10.35 -> 10.4
            stateViewModel.duslov1 > 9.35 -> 10.3
            stateViewModel.duslov1 > 7.35 -> 8.4
            stateViewModel.duslov1 > 6.15 -> 6.3
            stateViewModel.duslov1 > 5.95 -> 6.0
            stateViewModel.duslov1 > 5.6 -> 5.9
            else -> stateViewModel.vibrNak
        }

        stateViewModel.vsp2 = if (stateViewModel.srznach > 0) {
            val value = 0.00245 * stateViewModel.dreal.pow(2) * stateViewModel.srznach * (patmValue * 133.3 + plsrValue * 9.807) / (273.2 + tsrValue) *
                    sqrt((273.2 + taspValue) / (patmValue * 133.3 + stateViewModel.result * 9.807))
            when {
                value >= 20 -> 20.0
                value > 1 -> round(value)
                else -> round(value * 10) / 10.0
            }
        } else 0.0
    }

    fun calculateInnerTipVp(
        selectedDiameter: Double,
        patm: Double?,
        plsr: Double?,
        tsr: Double?,
        tasp: Double?,
        preom: Double?,
        stateViewModel: StateViewModel
    ) {
        val patmValue = (patm ?: 0.0) * 133.32
        val plsrValue = plsr ?: 0.0
        val tsrValue = tsr ?: 0.0
        val taspValue = tasp ?: 0.0
        val preomValue = preom ?: 0.0

        Log.d("CalculationViewModel", "Начальные данные для расчета:")
        Log.d("CalculationViewModel", "Выбранный диаметр: $selectedDiameter")
        Log.d("CalculationViewModel", "P атм: $patmValue")
        Log.d("CalculationViewModel", "P среды: $plsrValue")
        Log.d("CalculationViewModel", "T среды: $tsrValue")
        Log.d("CalculationViewModel", "T асп: $taspValue")
        Log.d("CalculationViewModel", "P реом: $preomValue")
        Log.d("CalculationViewModel", "Средняя скорость: ${stateViewModel.srznach}")

        val vp = if (patmValue > 0) {
            0.00245 * selectedDiameter.pow(2) * stateViewModel.srznach * ((patmValue + plsrValue) / (273 + tsrValue)) *
                    sqrt((1.293 * (273 + taspValue)) / (1.293 * (patmValue - preomValue)))
        } else 0.0

        stateViewModel.selectedDiameter = selectedDiameter
        stateViewModel.vpOfSelectedDiameter = vp

        Log.d("CalculationViewModel", "vp выбранного наконечника: $vp")
    }
}


