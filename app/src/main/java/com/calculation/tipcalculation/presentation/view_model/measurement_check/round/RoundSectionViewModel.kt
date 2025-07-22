package com.calculation.tipcalculation.presentation.view_model.measurement_check.round

import androidx.lifecycle.ViewModel
import com.calculation.tipcalculation.domain.model.RoundInputData
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.CalculateRoundSectionUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.result.ClearRoundInputUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.result.GetRoundInputUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.round.result.SetRoundInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RoundSectionViewModel @Inject constructor(
    private val calculateUseCase: CalculateRoundSectionUseCase,
    private val setInputUseCase: SetRoundInputUseCase,
    getInputUseCase: GetRoundInputUseCase,
    private val clearInputUseCase: ClearRoundInputUseCase
) : ViewModel() {

    val input: StateFlow<RoundInputData?> = getInputUseCase()

    fun calculate(d: String, l: String) {
        val dDouble = d.toDoubleOrNull()
        val lDouble = l.toDoubleOrNull()

        if (dDouble == null || lDouble == null) return

        val result = calculateUseCase(dDouble, lDouble) ?: return

        val enrichedInput = RoundInputData(
            d = d,
            l = l,
            dDouble = result.d,
            de = result.de,
            lOverDe = result.lOverDe,
            lz = result.lz,
            rule = result.rule,
            ki = result.ki
        )

        setInputUseCase(enrichedInput)
    }

    fun clear() {
        clearInputUseCase()
    }
}