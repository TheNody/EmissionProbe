package com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.history

import com.calculation.tipcalculation.domain.model.RectangularResultHistory
import com.calculation.tipcalculation.domain.repository.RectangularResultHistoryRepository

class DeleteRectangularResultHistoryUseCase(
    private val repository: RectangularResultHistoryRepository
) {
    suspend operator fun invoke(result: RectangularResultHistory) {
        repository.delete(result)
    }
}
