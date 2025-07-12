package com.calculation.tipcalculation.domain.usecase.internal.internal_result

import com.calculation.tipcalculation.domain.repository.InternalResultRepository

class ClearInternalResultUseCase(
    private val repository: InternalResultRepository
) {
    operator fun invoke() {
        repository.clear()
    }
}
