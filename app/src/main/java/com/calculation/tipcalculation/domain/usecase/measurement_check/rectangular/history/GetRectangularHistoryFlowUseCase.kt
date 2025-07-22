package com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular.history

import com.calculation.tipcalculation.domain.model.RectangularResultHistory
import com.calculation.tipcalculation.domain.repository.RectangularResultHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetRectangularHistoryFlowUseCase(
    private val repository: RectangularResultHistoryRepository
) {
    operator fun invoke(): Flow<List<RectangularResultHistory>> {
        return repository.getAllFlow()
    }
}
