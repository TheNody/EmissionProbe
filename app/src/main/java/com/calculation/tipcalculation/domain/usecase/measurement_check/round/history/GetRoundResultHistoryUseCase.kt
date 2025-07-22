package com.calculation.tipcalculation.domain.usecase.measurement_check.round.history

import com.calculation.tipcalculation.domain.model.RoundResultHistory
import com.calculation.tipcalculation.domain.repository.RoundResultHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetRoundResultHistoryUseCase(
    private val repository: RoundResultHistoryRepository
) {
    operator fun invoke(): Flow<List<RoundResultHistory>> {
        return repository.getAllFlow()
    }
}
