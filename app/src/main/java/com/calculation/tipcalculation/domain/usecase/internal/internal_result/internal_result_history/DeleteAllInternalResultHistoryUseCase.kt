package com.calculation.tipcalculation.domain.usecase.internal_result.internal_result_history

import com.calculation.tipcalculation.domain.repository.InternalResultHistoryRepository

class DeleteAllInternalResultHistoryUseCase(
    private val repository: InternalResultHistoryRepository
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}
