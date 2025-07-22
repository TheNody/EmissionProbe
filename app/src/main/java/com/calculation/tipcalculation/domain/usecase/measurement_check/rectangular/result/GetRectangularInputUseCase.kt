package com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result

import com.calculation.tipcalculation.domain.model.RectangularInputData
import com.calculation.tipcalculation.domain.repository.RectangularInputRepository
import kotlinx.coroutines.flow.StateFlow

class GetRectangularInputUseCase(
    private val repository: RectangularInputRepository
) {
    operator fun invoke(): StateFlow<RectangularInputData?> {
        return repository.getInput()
    }
}
