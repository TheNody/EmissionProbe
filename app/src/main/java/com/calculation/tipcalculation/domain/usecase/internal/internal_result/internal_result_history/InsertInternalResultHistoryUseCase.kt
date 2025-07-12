package com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history

import com.calculation.tipcalculation.domain.model.InternalResultHistory
import com.calculation.tipcalculation.domain.repository.InternalResultHistoryRepository

class InsertInternalResultHistoryUseCase(
    private val repository: InternalResultHistoryRepository
) {
    suspend operator fun invoke(result: InternalResultHistory) {
        repository.insert(result)
    }
}