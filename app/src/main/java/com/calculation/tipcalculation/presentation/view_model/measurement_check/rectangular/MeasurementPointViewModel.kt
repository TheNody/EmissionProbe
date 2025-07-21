package com.calculation.tipcalculation.presentation.view_model.measurement_check.rectangular

import androidx.lifecycle.ViewModel
import com.calculation.tipcalculation.domain.model.MeasurementInput
import com.calculation.tipcalculation.domain.model.MeasurementResult
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.CalculateMeasurementPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MeasurementPointViewModel @Inject constructor(
    private val useCase: CalculateMeasurementPointsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MeasurementResult?>(null)
    val state: StateFlow<MeasurementResult?> = _state

    fun calculate(a: Double, b: Double, l: Double) {
        val result = useCase(MeasurementInput(a, b, l))
        _state.update { result }
    }
}
