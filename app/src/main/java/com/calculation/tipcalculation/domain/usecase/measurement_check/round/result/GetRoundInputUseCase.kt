package com.calculation.tipcalculation.domain.usecase.measurement_check.round.result

import com.calculation.tipcalculation.domain.model.RoundInputData
import com.calculation.tipcalculation.domain.repository.RoundInputRepository
import kotlinx.coroutines.flow.StateFlow

class GetRoundInputUseCase(private val repository: RoundInputRepository) {
    operator fun invoke(): StateFlow<RoundInputData?> {
        return repository.getInput()
    }
}