package com.calculation.tipcalculation.presentation.view_model.measurement_check.rectangular.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.RectangularResultHistory
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.history.InsertRectangularResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result.ClearRectangularInputUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result.GetRectangularInputUseCase
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
class RectangularResultViewModel @Inject constructor(
    getInputUseCase: GetRectangularInputUseCase,
    private val insertHistoryUseCase: InsertRectangularResultHistoryUseCase,
    private val clearInputUseCase: ClearRectangularInputUseCase
) : ViewModel() {

    val result: StateFlow<RectangularResultHistory?> =
        getInputUseCase().map { input ->
            if (
                input?.de != null && input.lOverDe != null &&
                input.lz != null && input.rule != null
            ) {
                RectangularResultHistory(
                    a = input.a.toDoubleOrNull() ?: return@map null,
                    b = input.b.toDoubleOrNull() ?: return@map null,
                    l = input.l.toDoubleOrNull() ?: return@map null,
                    de = input.de,
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
