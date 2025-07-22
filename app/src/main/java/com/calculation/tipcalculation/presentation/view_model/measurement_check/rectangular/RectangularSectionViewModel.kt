package com.calculation.tipcalculation.presentation.view_model.measurement_check.rectangular

import androidx.lifecycle.ViewModel
import com.calculation.tipcalculation.domain.model.MeasurementInput
import com.calculation.tipcalculation.domain.model.RectangularInputData
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.CalculateMeasurementPointsUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result.ClearRectangularInputUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result.GetRectangularInputUseCase
import com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result.SetRectangularInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RectangularSectionViewModel @Inject constructor(
    private val calculateUseCase: CalculateMeasurementPointsUseCase,
    private val setInputUseCase: SetRectangularInputUseCase,
    getInputUseCase: GetRectangularInputUseCase,
    private val clearInputUseCase: ClearRectangularInputUseCase
) : ViewModel() {

    val input: StateFlow<RectangularInputData?> = getInputUseCase()

    fun calculate(a: String, b: String, l: String) {
        val aDouble = a.toDoubleOrNull()
        val bDouble = b.toDoubleOrNull()
        val lDouble = l.toDoubleOrNull()

        if (aDouble == null || bDouble == null || lDouble == null) return

        val result = calculateUseCase(MeasurementInput(aDouble, bDouble, lDouble))

        val enrichedInput = RectangularInputData(
            a = a,
            b = b,
            l = l,
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
