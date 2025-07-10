package com.calculation.tipcalculation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTip
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import com.calculation.tipcalculation.calculation.CalculationUseCase
import com.calculation.tipcalculation.model.CalculationData
import com.calculation.tipcalculation.model.CalculationState
import com.calculation.tipcalculation.model.ReportData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

class CalculationViewModel(
    private val calculationUseCase: CalculationUseCase = CalculationUseCase()
) : ViewModel() {

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

    private var calculationDataState = mutableStateOf(CalculationData())


    fun returnVariantNumber(): Int {
        return when {
            !calculationState.value.externalCalculationDone && !calculationState.value.internalCalculationDone -> 0
            calculationState.value.externalCalculationDone && !calculationState.value.internalCalculationDone -> 1
            !calculationState.value.externalCalculationDone && calculationState.value.internalCalculationDone -> 3
            calculationState.value.externalCalculationDone && calculationState.value.internalCalculationDone -> 2
            else -> 0
        }
    }

    fun setFilterTips(filterTips: List<ExternalFilterTip>) {
        diameters = filterTips.map { it.value }
        Log.d("CalculationViewModel", "Получены значения внешних фильтров: $diameters")
    }

    fun setExternalFilterTips(filterTips: List<FilterTip>) {
        externalDiameters = filterTips.map { it.value }
        Log.d("CalculationViewModel", "Получены значения внутренних фильтров: $externalDiameters")
    }

    fun calculateResult(
        reportData: ReportData,
        speeds: List<Double>,
        patm: Double?,
        preom: Double?,
        settingsViewModel: SettingsViewModel
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                settingsViewModel.resetValues()

                val patmValue = (patm ?: 0.0) * 133.32
                val plsrValue = reportData.plsr.value
                val tsrValue = reportData.tsr.value
                val taspValue = reportData.tasp.value
                val preomValue = preom ?: 0.0

                val srznach = speeds.average()
                val averageValue = if (speeds.isNotEmpty()) 24 / sqrt(srznach) else 0.0

                val sigmaValue = calculationUseCase.calculateSigma(speeds, srznach)
                val skoValue = calculationUseCase.calculateSKO(speeds)
                val diametersResult = calculationUseCase.calculateDiametersStuff(
                    diameters,
                    patmValue,
                    plsrValue,
                    tsrValue,
                    taspValue,
                    preomValue,
                    srznach,
                    averageValue
                )
                val otherResult = calculationUseCase.calculateOtherValues(
                    patmValue,
                    plsrValue,
                    tsrValue,
                    taspValue,
                    srznach,
                    averageValue
                )

                val newData = calculationDataState.value.copy(
                    patm = patmValue,
                    srznach = srznach,
                    sigma = sigmaValue,
                    sko = skoValue,
                    average = averageValue,

                    closestDiameter = diametersResult.closestDiameter,
                    firstSuitableDiameter = diametersResult.firstSuitableDiameter,
                    suitableDiameters = diametersResult.suitableDiameters,
                    unsuitableDiameters = diametersResult.unsuitableDiameters,
                    checkedDiametersList = diametersResult.checkedDiametersList,
                    selectedVp = diametersResult.selectedVp,

                    calculatedTip = otherResult.calculatedTip,
                    tipSize = otherResult.tipSize,
                    aspUsl = otherResult.aspUsl,
                    result = otherResult.resultValue,
                    aspUsl1 = otherResult.aspUsl1,
                    duslov1 = otherResult.duslov1,
                    vibrNak = otherResult.vibrNak,
                    dreal = otherResult.dreal,
                    vsp2 = otherResult.vsp2
                )

                withContext(Dispatchers.Main) {
                    calculationDataState.value = newData
                    settingsViewModel.data = newData

                    Log.d("CalculationViewModel", "P атм в Па: $patmValue")
                    Log.d("CalculationViewModel", "Идеальный наконечник: $averageValue")
                    Log.d("CalculationViewModel", "Ближайший наконечник: ${diametersResult.closestDiameter}")
                    Log.d("CalculationViewModel", "Первый подходящий наконечник: ${diametersResult.firstSuitableDiameter}")
                    Log.d("CalculationViewModel", "Подходящие: ${diametersResult.suitableDiameters}")
                    Log.d("CalculationViewModel", "Неподходящие: ${diametersResult.unsuitableDiameters}")
                }
            } catch (e: Exception) {
                Log.e("CalculationViewModel", "Ошибка при вычислении результата: ${e.localizedMessage}")
            }
        }
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
        viewModelScope.launch(Dispatchers.Default) {
            try {
                settingsViewModel.resetValues()
                val nonNullSpeeds = speeds.filterNotNull()

                val patmValue = (patm ?: 0.0) * 133.32
                val plsrValue = plsr ?: 0.0
                val tsrValue = tsr ?: 0.0
                val taspValue = tasp ?: 0.0
                val preomValue = preom ?: 0.0

                val srznach = nonNullSpeeds.average()

                val vp = calculationUseCase.calculateVp(
                    diameter = selectedDiameter,
                    patmValue = patmValue,
                    plsrValue = plsrValue,
                    tsrValue = tsrValue,
                    taspValue = taspValue,
                    preomValue = preomValue,
                    srznach = srznach
                )

                val newData = calculationDataState.value.copy(
                    selectedDiameter = selectedDiameter,
                    selectedVp = vp
                )

                withContext(Dispatchers.Main) {
                    calculationDataState.value = newData
                    Log.d("CalculationViewModel", "vp выбранного наконечника: $vp")
                }
            } catch (e: Exception) {
                Log.e("CalculationViewModel", "Ошибка при расчете внутреннего наконечника: ${e.localizedMessage}")
            }
        }
    }

    fun prepareReportData(
        reportData: ReportData,
        speeds: List<Double?>
    ): ReportData {

        val nonNullSpeeds = speeds.filterNotNull()

        val tsrValue = reportData.tsr.value
        val taspValue = reportData.tasp.value
        val patmValue = reportData.patm * 133.32 * 0.001
        val plsrValue = reportData.plsr.value * 0.001

        val srznach = nonNullSpeeds.average()
        val skoVal = calculationUseCase.calculateSKO(nonNullSpeeds)

        val calcData = calculationDataState.value

        val finalCalculatedTip = calcData.calculatedTip
        val finalFirstSuitableTip = calcData.firstSuitableDiameter

        val newReportData = ReportData(
            patm = patmValue,
            tsr = mutableDoubleStateOf(tsrValue),
            tasp = mutableDoubleStateOf(taspValue),
            averageSpeed = srznach,
            measurementCount = nonNullSpeeds.size,
            plsr = mutableDoubleStateOf(plsrValue),
            calculatedTip = finalCalculatedTip,
            firstSuitableTip = finalFirstSuitableTip,
            sko = skoVal
        )

        Log.d("CalculationViewModel", "Подготовка отчёта:")
        Log.d("CalculationViewModel", "P атм (кПа): $patmValue")
        Log.d("CalculationViewModel", "T среды (°C): $tsrValue")
        Log.d("CalculationViewModel", "T асп (°C): $taspValue")
        Log.d("CalculationViewModel", "Кол-во измерений: ${nonNullSpeeds.size}")
        Log.d("CalculationViewModel", "Средняя скорость (м/с): $srznach")
        Log.d("CalculationViewModel", "P среды (кПа): $plsrValue")
        Log.d("CalculationViewModel", "Рассчитанный наконечник: $finalCalculatedTip")
        Log.d("CalculationViewModel", "Первый подходящий наконечник: $finalFirstSuitableTip")
        Log.d("CalculationViewModel", "СКО: $skoVal")

        return newReportData
    }
}
