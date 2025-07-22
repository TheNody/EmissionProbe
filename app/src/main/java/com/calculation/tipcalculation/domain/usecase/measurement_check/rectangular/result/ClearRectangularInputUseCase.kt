package com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.result

import com.calculation.tipcalculation.domain.repository.RectangularInputRepository

class ClearRectangularInputUseCase(
    private val repository: RectangularInputRepository
) {
    operator fun invoke() {
        repository.clear()
    }
}
