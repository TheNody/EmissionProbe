package com.calculation.tipcalculation.presentation.view_model.measurement_check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.RectangularResultHistory
import com.calculation.tipcalculation.domain.model.RoundResultHistory
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.history.DeleteRectangularResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.history.GetRectangularHistoryFlowUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.history.DeleteRoundResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.history.GetRoundResultHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionTypeSelectionViewModel @Inject constructor(
    getRoundResultHistoryUseCase: GetRoundResultHistoryUseCase,
    private val deleteRoundResultHistoryUseCase: DeleteRoundResultHistoryUseCase,
    getRectangularResultHistoryUseCase: GetRectangularHistoryFlowUseCase,
    private val deleteRectangularResultHistoryUseCase: DeleteRectangularResultHistoryUseCase
) : ViewModel() {

    val roundHistory: StateFlow<List<RoundResultHistory>> = getRoundResultHistoryUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val rectangularHistory: StateFlow<List<RectangularResultHistory>> = getRectangularResultHistoryUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteRoundResult(item: RoundResultHistory) {
        viewModelScope.launch {
            deleteRoundResultHistoryUseCase(item)
        }
    }

    fun deleteRectangularResult(item: RectangularResultHistory) {
        viewModelScope.launch {
            deleteRectangularResultHistoryUseCase(item)
        }
    }
}
