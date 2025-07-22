package com.calculation.tipcalculation.domain.usecase.measurement_check.round.history

import com.calculation.tipcalculation.domain.repository.RoundResultHistoryRepository

class DeleteAllRoundResultHistoryUseCase(
    private val repository: RoundResultHistoryRepository
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}
