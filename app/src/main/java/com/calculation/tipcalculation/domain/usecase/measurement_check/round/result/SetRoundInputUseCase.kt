package com.calculation.tipcalculation.domain.usecase.measurement_check.round.result

import com.calculation.tipcalculation.domain.model.RoundInputData
import com.calculation.tipcalculation.domain.repository.RoundInputRepository

class SetRoundInputUseCase(private val repository: RoundInputRepository) {
    operator fun invoke(data: RoundInputData) {
        repository.setInput(data)
    }
}
