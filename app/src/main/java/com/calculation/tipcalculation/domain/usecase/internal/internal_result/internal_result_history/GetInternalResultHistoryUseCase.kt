package com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history

import com.calculation.tipcalculation.domain.model.InternalResultHistory
import com.calculation.tipcalculation.domain.repository.InternalResultHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetInternalResultHistoryUseCase(
    private val repository: InternalResultHistoryRepository
) {
    operator fun invoke(): Flow<List<InternalResultHistory>> {
        return repository.getAllFlow()
    }
}