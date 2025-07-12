package com.calculation.tipcalculation.domain.usecase.external_result.external_result_history

import com.calculation.tipcalculation.domain.repository.ExternalResultHistoryRepository

class DeleteAllExternalResultHistoryUseCase(
    private val repository: ExternalResultHistoryRepository
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}