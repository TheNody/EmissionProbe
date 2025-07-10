package com.calculation.tipcalculation.presentation.view_model.internal_screen.internal_calc_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.usecase.speed_count.GetSpeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternalCalcViewModel @Inject constructor(
    private val getSpeedUseCase: GetSpeedUseCase
) : ViewModel() {

    private val _speedCount = MutableStateFlow(0)
    val speedCount: StateFlow<Int> get() = _speedCount

    init {
        loadSpeedCount()
    }

    private fun loadSpeedCount() {
        viewModelScope.launch {
            val result = getSpeedUseCase()
            _speedCount.value = result?.speedCount ?: 0
        }
    }
}
