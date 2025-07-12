package com.calculation.tipcalculation.presentation.view_model.external_screen.external_calc_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.ExternalResultData
import com.calculation.tipcalculation.domain.usecase.external.external_calc.ExternalCalculationUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.ClearExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.GetExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.SetExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.speed_count.GetSpeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExternalCalcViewModel @Inject constructor(
    private val getSpeedUseCase: GetSpeedUseCase,
    private val externalCalculationUseCase: ExternalCalculationUseCase,
    private val setExternalResultUseCase: SetExternalResultUseCase,
    private val getExternalResultUseCase: GetExternalResultUseCase,
    private val clearExternalResultUseCase: ClearExternalResultUseCase
) : ViewModel() {

    private val _speedCount = MutableStateFlow(0)
    val speedCount: StateFlow<Int> = _speedCount

    val savedResult: StateFlow<ExternalResultData?> = getExternalResultUseCase()

    init {
        loadSpeedCount()
    }

    private fun loadSpeedCount() {
        viewModelScope.launch {
            val result = getSpeedUseCase()
            _speedCount.value = result?.speedCount ?: 0
        }
    }

    fun calculateExternalResult(
        fieldValues: List<String>,
        speeds: List<String>
    ) {
        viewModelScope.launch {
            try {
                val patm = fieldValues.getOrNull(0)?.toDoubleOrNull() ?: 0.0
                val plsr = fieldValues.getOrNull(1)?.toDoubleOrNull() ?: 0.0
                val tsr = fieldValues.getOrNull(2)?.toDoubleOrNull() ?: 0.0
                val tasp = fieldValues.getOrNull(3)?.toDoubleOrNull() ?: 0.0
                val preom = fieldValues.getOrNull(4)?.toDoubleOrNull() ?: 0.0

                val speedDoubles = speeds.mapNotNull { it.toDoubleOrNull() }

                val result = externalCalculationUseCase.calculateExternal(
                    speeds = speedDoubles,
                    patm = patm,
                    plsr = plsr,
                    tsr = tsr,
                    tasp = tasp,
                    preom = preom
                )

                Log.d("ExternalCalcViewModel", "Результат: $result")

                setExternalResultUseCase(result)

            } catch (e: Exception) {
                Log.e("ExternalCalcViewModel", "Ошибка при расчёте: ${e.message}", e)
            }
        }
    }

    fun clearResult() {
        clearExternalResultUseCase()
    }
}

