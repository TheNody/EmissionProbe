package com.calculation.tipcalculation.domain.usecase.internal.internal_result

import com.calculation.tipcalculation.domain.model.InternalResultData
import com.calculation.tipcalculation.domain.repository.InternalResultRepository
import kotlinx.coroutines.flow.StateFlow

class GetInternalResultUseCase(
    private val repository: InternalResultRepository
) {
    operator fun invoke(): StateFlow<InternalResultData?> {
        return repository.getResult()
    }
}
