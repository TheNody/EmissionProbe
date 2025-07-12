package com.calculation.tipcalculation.domain.usecase.external_result.external_result_history

import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.repository.ExternalResultHistoryRepository

class DeleteExternalResultHistoryUseCase(
    private val repository: ExternalResultHistoryRepository
) {
    suspend operator fun invoke(result: ExternalResultHistory) {
        repository.delete(result)
    }
}
