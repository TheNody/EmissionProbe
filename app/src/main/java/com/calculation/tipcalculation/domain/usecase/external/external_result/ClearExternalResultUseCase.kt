package com.calculation.tipcalculation.domain.usecase.external_result

import com.calculation.tipcalculation.domain.repository.ExternalResultRepository

class ClearExternalResultUseCase(
    private val repository: ExternalResultRepository
) {
    operator fun invoke() {
        repository.clear()
    }
}