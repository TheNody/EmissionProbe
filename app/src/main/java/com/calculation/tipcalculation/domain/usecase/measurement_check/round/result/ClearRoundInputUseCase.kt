package com.calculation.tipcalculation.domain.usecase.measurement_check.round.result

import com.calculation.tipcalculation.domain.repository.RoundInputRepository

class ClearRoundInputUseCase(private val repository: RoundInputRepository) {
    operator fun invoke() {
        repository.clear()
    }
}