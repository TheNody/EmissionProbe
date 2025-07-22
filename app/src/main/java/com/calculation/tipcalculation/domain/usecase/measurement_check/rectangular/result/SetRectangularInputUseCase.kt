package com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result

import com.calculation.tipcalculation.domain.model.RectangularInputData
import com.calculation.tipcalculation.domain.repository.RectangularInputRepository

class SetRectangularInputUseCase(
    private val repository: RectangularInputRepository
) {
    operator fun invoke(data: RectangularInputData) {
        repository.setInput(data)
    }
}
