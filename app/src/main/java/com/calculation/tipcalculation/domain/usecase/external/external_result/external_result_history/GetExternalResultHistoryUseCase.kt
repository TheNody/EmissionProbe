package com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history

import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.repository.ExternalResultHistoryRepository

class GetExternalResultHistoryUseCase(
    private val repository: ExternalResultHistoryRepository
) {
    suspend operator fun invoke(): List<ExternalResultHistory> {
        return repository.getAll()
    }
}
