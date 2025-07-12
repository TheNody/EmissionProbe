package com.calculation.tipcalculation.domain.usecase.internal.internal_result

import com.calculation.tipcalculation.domain.model.InternalResultData
import com.calculation.tipcalculation.domain.repository.InternalResultRepository

class SetInternalResultUseCase(
    private val repository: InternalResultRepository
) {
    operator fun invoke(data: InternalResultData) {
        repository.setResult(data)
    }
}
