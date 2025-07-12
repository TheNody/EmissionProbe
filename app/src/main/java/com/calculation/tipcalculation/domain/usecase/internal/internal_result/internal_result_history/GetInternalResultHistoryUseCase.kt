package com.calculation.tipcalculation.domain.usecase.internal_result.internal_result_history

import com.calculation.tipcalculation.domain.model.InternalResultHistory
import com.calculation.tipcalculation.domain.repository.InternalResultHistoryRepository

class GetInternalResultHistoryUseCase(
    private val repository: InternalResultHistoryRepository
) {
    suspend operator fun invoke(): List<InternalResultHistory> {
        return repository.getAll()
    }
}