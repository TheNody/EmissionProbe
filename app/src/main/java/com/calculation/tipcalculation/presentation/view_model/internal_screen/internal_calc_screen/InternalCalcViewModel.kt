package com.calculation.tipcalculation.presentation.view_model.internal_screen.internal_calc_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.FilterTip
import com.calculation.tipcalculation.domain.model.InternalResultData
import com.calculation.tipcalculation.domain.usecase.internal_calc.InternalCalculationUseCase
import com.calculation.tipcalculation.domain.usecase.internal_filter.GetFilterTipsUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.ClearInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.GetInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.SetInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.speed_count.GetSpeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternalCalcViewModel @Inject constructor(
    private val getSpeedUseCase: GetSpeedUseCase,
    private val getFilterTipsUseCase: GetFilterTipsUseCase,
    private val internalCalculationUseCase: InternalCalculationUseCase,
    private val setInternalResultUseCase: SetInternalResultUseCase,
    private val getInternalResultUseCase: GetInternalResultUseCase,
    private val clearInternalResultUseCase: ClearInternalResultUseCase
) : ViewModel() {

    private val _speedCount = MutableStateFlow(0)
    val speedCount: StateFlow<Int> = _speedCount

    private val _filterTips = MutableStateFlow<List<FilterTip>>(emptyList())
    val filterTips: StateFlow<List<FilterTip>> = _filterTips

    val savedResult: StateFlow<InternalResultData?> = getInternalResultUseCase()

    init {
        loadSpeedCount()
        observeFilterTips()
    }

    fun clearResult() {
        clearInternalResultUseCase()
    }

    private fun loadSpeedCount() {
        viewModelScope.launch {
            val result = getSpeedUseCase()
            _speedCount.value = result?.speedCount ?: 0
        }
    }

    private fun observeFilterTips() {
        viewModelScope.launch {
            getFilterTipsUseCase().asFlow().collect { tips ->
                _filterTips.value = tips
            }
        }
    }

    fun calculateInternalResult(
        selectedTip: Double?,
        fieldValues: List<String>,
        speeds: List<String>
    ) {
        viewModelScope.launch {
            if (selectedTip == null) {
                Log.d("InternalCalcViewModel", "Наконечник не выбран")
                return@launch
            }

            val patm = fieldValues.getOrNull(0)?.toDoubleOrNull()
            val tsr = fieldValues.getOrNull(1)?.toDoubleOrNull()
            val tasp = fieldValues.getOrNull(2)?.toDoubleOrNull()
            val plsr = fieldValues.getOrNull(3)?.toDoubleOrNull()
            val preom = fieldValues.getOrNull(4)?.toDoubleOrNull()

            val inputValid = listOf(patm, tsr, tasp, plsr, preom).all { it != null }
            if (!inputValid) {
                Log.d("InternalCalcViewModel", "Некорректные входные данные")
                return@launch
            }

            val speedValues = speeds.mapNotNull { it.toDoubleOrNull() }
            if (speedValues.isEmpty()) {
                Log.d("InternalCalcViewModel", "Скорости не введены")
                return@launch
            }

            val vp = internalCalculationUseCase.calculateVpForSelectedTip(
                selectedDiameter = selectedTip,
                speeds = speedValues,
                patm = patm!!,
                plsr = plsr!!,
                tsr = tsr!!,
                tasp = tasp!!,
                preom = preom!!
            )

            val srznach = speedValues.average()
            val averageTip = if (srznach > 0) 24 / kotlin.math.sqrt(srznach) else 0.0

            Log.d("InternalCalcViewModel", "Средняя скорость: $srznach м/с")
            Log.d("InternalCalcViewModel", "Идеальный наконечник: $averageTip")
            Log.d("InternalCalcViewModel", "Выбранный диаметр: $selectedTip")
            Log.d("InternalCalcViewModel", "VP выбранного наконечника: $vp")

            val result = InternalResultData(
                patm = patm,
                tsr = tsr,
                tasp = tasp,
                plsr = plsr,
                preom = preom,
                speeds = speedValues,
                averageSpeed = srznach,
                averageTip = averageTip,
                selectedTip = selectedTip,
                vp = vp
            )

            setInternalResultUseCase(result)

            Log.d("InternalCalcViewModel", "Результат вычисления установлен")
        }
    }
}