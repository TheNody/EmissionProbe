package com.calculation.tipcalculation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTip
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import com.calculation.tipcalculation.model.CalculationData
import com.calculation.tipcalculation.model.CalculationState
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class CalculationViewModel : ViewModel() {
    var patm: MutableState<String> = mutableStateOf("")
    var plsr: MutableState<String> = mutableStateOf("")
    var tsr: MutableState<String> = mutableStateOf("")
    var tasp: MutableState<String> = mutableStateOf("")
    var preom: MutableState<String> = mutableStateOf("")
    var selectedInnerTip: MutableState<String> = mutableStateOf("")
    var isButtonVisible: MutableState<Boolean> = mutableStateOf(false)
    var calculationState: MutableState<CalculationState> = mutableStateOf(CalculationState())

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
        settingsViewModel: SettingsViewModel
    ) {
        settingsViewModel.resetValues()

        val data = settingsViewModel.data
        val nonNullSpeeds = speeds.filterNotNull()
        val (patmValue, plsrValue, tsrValue, taspValue, preomValue) = listOf(
            patm ?: 0.0, plsr ?: 0.0, tsr ?: 0.0, tasp ?: 0.0, preom ?: 0.0
        )

        data.patm.value = patmValue
        data.srznach.value = nonNullSpeeds.average()
        data.average.value = if (nonNullSpeeds.isNotEmpty()) 24 / sqrt(data.srznach.value) else 0.0
        data.sigma.value = calculateSigma(nonNullSpeeds, data.srznach.value)

        calculateDiameters(diameters, patmValue, plsrValue, tsrValue, taspValue, preomValue, data)
        calculateAspUsl(patmValue, plsrValue, tsrValue, taspValue, data)
        calculateResultValue(patmValue, plsrValue, tsrValue, taspValue, data)

        Log.d("CalculationViewModel", "P атм в Па: $patmValue")
        Log.d("CalculationViewModel", "Идеальный наконечник: ${data.average.value}")
        Log.d("CalculationViewModel", "Ближайший наконечник: ${data.closestDiameter.value}")
        Log.d("CalculationViewModel", "Самый первый ближайший подходящий наконечник: ${data.firstSuitableDiameter.value}")
        Log.d("CalculationViewModel", "Подходящие наконечники: ${data.suitableDiameters.joinToString { it.value.toString() }}")
        Log.d("CalculationViewModel", "Неподходящие наконечники: ${data.unsuitableDiameters.joinToString { it.value.toString() }}")
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
        data: CalculationData
    ) {
        data.suitableDiameters.clear()
        data.unsuitableDiameters.clear()
        data.checkedDiametersList.clear()

        data.closestDiameter.value = diameters.minByOrNull { abs(it - data.average.value) } ?: 0.0
        val closestIndex = diameters.indexOf(data.closestDiameter.value)
        val checkedDiameters = mutableSetOf<Double>()
        data.firstSuitableDiameter.value = 0.0

        for (step in diameters.indices) {
            checkDiameter(closestIndex + step, checkedDiameters, diameters, patmValue, plsrValue, tsrValue, taspValue, preomValue, data)
            checkDiameter(closestIndex - step, checkedDiameters, diameters, patmValue, plsrValue, tsrValue, taspValue, preomValue, data)
        }

        calculateTipSize(data)

        val firstSuitable = data.suitableDiameters
            .map { it.value }
            .filter { it <= 20 }
            .minByOrNull { abs(it - data.average.value) }

        data.firstSuitableDiameter.value = firstSuitable ?: 0.0
        data.selectedVp.value = calculateVp(data.firstSuitableDiameter.value, patmValue, plsrValue, tsrValue, taspValue, preomValue, data.srznach.value)
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
        data: CalculationData
    ) {
        if (index in diameters.indices) {
            val currentDiameter = diameters[index]
            if (currentDiameter !in checkedDiameters) {
                val vp = calculateVp(currentDiameter, patmValue, plsrValue, tsrValue, taspValue, preomValue, data.srznach.value)
                data.checkedDiametersList.add(mutableDoubleStateOf(currentDiameter) to mutableDoubleStateOf(vp))
                if (vp <= 20) {
                    data.suitableDiameters.add(mutableDoubleStateOf(currentDiameter))
                } else {
                    data.unsuitableDiameters.add(mutableDoubleStateOf(currentDiameter))
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

    private fun calculateTipSize(data: CalculationData) {
        data.calculatedTip.value = when {
            data.average.value > 5.05 -> 5.3
            data.average.value > 4.55 -> 4.8
            data.average.value > 4.25 -> 4.3
            data.average.value > 4.1 -> 4.2
            else -> 4.0
        }

        data.tipSize.value = when {
            data.average.value > 12 -> 13.0
            data.average.value > 10.7 -> 11.0
            data.average.value > 10.35 -> 10.4
            data.average.value > 9.35 -> 10.3
            data.average.value > 7.35 -> 8.4
            data.average.value > 6.15 -> 6.3
            data.average.value > 5.95 -> 6.0
            data.average.value > 5.6 -> 5.9
            else -> data.calculatedTip.value
        }
    }

    private fun calculateAspUsl(
        patmValue: Double,
        plsrValue: Double,
        tsrValue: Double,
        taspValue: Double,
        data: CalculationData
    ) {
        data.aspUsl.value = if (data.srznach.value > 0) {
            val value = 0.00245 * data.tipSize.value.pow(2) * data.srznach.value * (patmValue * 133.3 + plsrValue * 9.807) / (273 + tsrValue) *
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
        data: CalculationData
    ) {
        val paspmm = if (data.srznach.value > 0) round((0.327 * data.aspUsl.value.pow(2) + 2.35 * data.aspUsl.value + 5.951) * 10) / 10.0 else 0.0
        data.result.value = if (data.srznach.value > 0) round((plsrValue - paspmm) * 10) / 10.0 else 0.0

        data.aspUsl1.value = if (data.srznach.value > 0) {
            val value = 0.00245 * data.tipSize.value.pow(2) * data.srznach.value * (patmValue * 133.3 + plsrValue * 9.807) / (273 + tsrValue) *
                    sqrt((273 + taspValue) / (patmValue * 133.3 + data.result.value * 9.807))
            when {
                value >= 20 -> 20.0
                value > 1 -> round(value)
                else -> round(value * 10) / 10.0
            }
        } else 0.0

        data.duslov1.value = if (data.srznach.value > 0) sqrt(data.aspUsl1.value / 0.00245 / data.srznach.value / (patmValue * 133.3 + plsrValue * 9.807) * (273 + tsrValue) /
                sqrt((273 + taspValue) / (patmValue * 133.3 + data.result.value * 9.807))) else 0.0

        data.vibrNak.value = when {
            data.duslov1.value > 5.05 -> 5.3
            data.duslov1.value > 4.55 -> 4.8
            data.duslov1.value > 4.25 -> 4.3
            data.duslov1.value > 4.1 -> 4.2
            else -> 4.0
        }

        data.dreal.value = when {
            data.duslov1.value > 12 -> 13.0
            data.duslov1.value > 10.7 -> 11.0
            data.duslov1.value > 10.35 -> 10.4
            data.duslov1.value > 9.35 -> 10.3
            data.duslov1.value > 7.35 -> 8.4
            data.duslov1.value > 6.15 -> 6.3
            data.duslov1.value > 5.95 -> 6.0
            data.duslov1.value > 5.6 -> 5.9
            else -> data.vibrNak.value
        }

        data.vsp2.value = if (data.srznach.value > 0) {
            val value = 0.00245 * data.dreal.value.pow(2) * data.srznach.value * (patmValue * 133.3 + plsrValue * 9.807) / (273.2 + tsrValue) *
                    sqrt((273.2 + taspValue) / (patmValue * 133.3 + data.result.value * 9.807))
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
        speeds: List<Double?>,
        settingsViewModel: SettingsViewModel
    ) {

        settingsViewModel.resetValues()

        val data = settingsViewModel.data
        val nonNullSpeeds = speeds.filterNotNull()
        val (patmValue, plsrValue, tsrValue, taspValue, preomValue) = listOf(
            (patm ?: 0.0) * 133.32, plsr ?: 0.0, tsr ?: 0.0, tasp ?: 0.0, preom ?: 0.0
        )

        data.patm.value = patmValue
        data.srznach.value = nonNullSpeeds.average()
        data.average.value = if (nonNullSpeeds.isNotEmpty()) 24 / sqrt(data.srznach.value) else 0.0
        data.sigma.value = calculateSigma(nonNullSpeeds, data.srznach.value)

        Log.d("CalculationViewModel", "Начальные данные для расчета:")
        Log.d("CalculationViewModel", "Выбранный диаметр: $selectedDiameter")
        Log.d("CalculationViewModel", "P атм: $patmValue")
        Log.d("CalculationViewModel", "P среды: $plsrValue")
        Log.d("CalculationViewModel", "T среды: $tsrValue")
        Log.d("CalculationViewModel", "T асп: $taspValue")
        Log.d("CalculationViewModel", "P реом: $preomValue")
        Log.d("CalculationViewModel", "Средняя скорость: ${data.srznach.value}")

        val vp = if (patmValue > 0) {
            0.00245 * selectedDiameter.pow(2) * data.srznach.value * ((patmValue + plsrValue) / (273 + tsrValue)) *
                    sqrt((1.293 * (273 + taspValue)) / (1.293 * (patmValue - preomValue)))
        } else 0.0

        data.selectedDiameter.value = selectedDiameter
        data.vpOfSelectedDiameter.value = vp

        Log.d("CalculationViewModel", "vp выбранного наконечника: ${data.vpOfSelectedDiameter.value}")
    }
}

