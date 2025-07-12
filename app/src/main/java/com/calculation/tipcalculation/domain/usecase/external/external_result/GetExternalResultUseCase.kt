package com.calculation.tipcalculation.domain.usecase.external.external_result

import com.calculation.tipcalculation.domain.model.ExternalResultData
import com.calculation.tipcalculation.domain.repository.ExternalResultRepository
import kotlinx.coroutines.flow.StateFlow

class GetExternalResultUseCase(
    private val repository: ExternalResultRepository
) {
    operator fun invoke(): StateFlow<ExternalResultData?> {
        return repository.getResult()
    }
}