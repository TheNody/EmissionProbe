package com.calculation.tipcalculation.presentation.view_model.internal_screen.internal_result_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.InternalResultData
import com.calculation.tipcalculation.domain.model.InternalResultHistory
import com.calculation.tipcalculation.domain.usecase.internal_result.ClearInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.GetInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.internal_result_history.InsertInternalResultHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class InternalResultViewModel @Inject constructor(
    getInternalResultUseCase: GetInternalResultUseCase,
    private val insertHistoryUseCase: InsertInternalResultHistoryUseCase,
    private val clearResultUseCase: ClearInternalResultUseCase
) : ViewModel() {

    val result: StateFlow<InternalResultData?> = getInternalResultUseCase()

    fun saveResultToHistory() {
        val data = result.value ?: return

        val timestamp = SimpleDateFormat("dd:MM:yyyy HH:mm", Locale.getDefault())
            .format(Date())

        val history = InternalResultHistory(
            patm = data.patm,
            tsr = data.tsr,
            tasp = data.tasp,
            plsr = data.plsr,
            preom = data.preom,
            speeds = data.speeds,
            averageSpeed = data.averageSpeed,
            averageTip = data.averageTip,
            selectedTip = data.selectedTip,
            vp = data.vp,
            timestamp = timestamp
        )

        viewModelScope.launch {
            insertHistoryUseCase(history)
            clearResultUseCase()
        }
    }
}