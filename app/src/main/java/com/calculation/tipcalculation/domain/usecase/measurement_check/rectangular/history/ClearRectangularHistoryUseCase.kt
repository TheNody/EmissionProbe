package com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.history

import com.calculation.tipcalculation.domain.repository.RectangularResultHistoryRepository

class ClearRectangularHistoryUseCase(
    private val repository: RectangularResultHistoryRepository
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}
