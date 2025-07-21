package com.calculation.tipcalculation.presentation.view_model.measurement_check.round

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.RoundSectionResult
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.CalculateRoundSectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoundSectionViewModel @Inject constructor(
    private val calculateUseCase: CalculateRoundSectionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RoundSectionResult?>(null)
    val state: StateFlow<RoundSectionResult?> = _state

    fun calculate(d: Double, l: Double) {
        viewModelScope.launch {
            _state.value = calculateUseCase(d, l)
        }
    }

    fun clear() {
        _state.value = null
    }
}
