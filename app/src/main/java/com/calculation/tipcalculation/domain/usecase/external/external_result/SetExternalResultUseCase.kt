package com.calculation.tipcalculation.domain.usecase.external.external_result

import com.calculation.tipcalculation.domain.model.ExternalResultData
import com.calculation.tipcalculation.domain.repository.ExternalResultRepository

class SetExternalResultUseCase(
    private val repository: ExternalResultRepository
) {
    operator fun invoke(data: ExternalResultData) {
        repository.setResult(data)
    }
}