package com.calculation.tipcalculation.domain.usecase.measurement_check.round.history

import com.calculation.tipcalculation.domain.model.RoundResultHistory
import com.calculation.tipcalculation.domain.repository.RoundResultHistoryRepository

class InsertRoundResultHistoryUseCase(
    private val repository: RoundResultHistoryRepository
) {
    suspend operator fun invoke(result: RoundResultHistory) {
        repository.insert(result)
    }
}
