package com.calculation.tipcalculation.presentation.view_model.measurement_check.round.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.RoundResultHistory
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.history.InsertRoundResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.result.ClearRoundInputUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.result.GetRoundInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RoundResultViewModel @Inject constructor(
    getRoundInputUseCase: GetRoundInputUseCase,
    private val insertHistoryUseCase: InsertRoundResultHistoryUseCase,
    private val clearInputUseCase: ClearRoundInputUseCase
) : ViewModel() {

    val result: StateFlow<RoundResultHistory?> =
        getRoundInputUseCase().map { input ->
            if (
                input?.dDouble != null &&
                input.de != null && input.lOverDe != null && input.lz != null && input.rule != null
            ) {
                RoundResultHistory(
                    d = input.dDouble,
                    de = input.de,
                    l = input.l.toDoubleOrNull() ?: return@map null,
                    lOverDe = input.lOverDe,
                    lz = input.lz,
                    rule = input.rule,
                    ki = input.ki?.kValues,
                    timestamp = ""
                )
            } else {
                null
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun saveResultToHistory() {
        val data = result.value ?: return

        val timestamp = SimpleDateFormat("dd:MM:yyyy HH:mm", Locale.getDefault())
            .format(Date())

        val history = data.copy(timestamp = timestamp)

        viewModelScope.launch {
            insertHistoryUseCase(history)
            clearInputUseCase()
        }
    }

    fun clearResult() {
        clearInputUseCase()
    }
}
