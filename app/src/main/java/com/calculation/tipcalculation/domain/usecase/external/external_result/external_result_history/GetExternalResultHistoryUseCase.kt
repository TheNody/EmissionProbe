package com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history

import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.repository.ExternalResultHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetExternalResultHistoryUseCase(
    private val repository: ExternalResultHistoryRepository
) {
    operator fun invoke(): Flow<List<ExternalResultHistory>> {
        return repository.getAllFlow()
    }
}