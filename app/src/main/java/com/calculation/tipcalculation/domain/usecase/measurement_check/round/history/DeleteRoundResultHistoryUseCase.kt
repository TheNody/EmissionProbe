package com.calculation.tipcalculation.domain.usecase.measurement_check.round.history

import com.calculation.tipcalculation.domain.model.RoundResultHistory
import com.calculation.tipcalculation.domain.repository.RoundResultHistoryRepository

class DeleteRoundResultHistoryUseCase(
    private val repository: RoundResultHistoryRepository
) {
    suspend operator fun invoke(result: RoundResultHistory) {
        repository.delete(result)
    }
}
